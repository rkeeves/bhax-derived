// [COMPILE]
// -Wl,-subsystem,windows -lmingw32 -lSDL2main -lSDL2
#include "sdl_wrapper.hpp"
#include <complex>
#include <cstdlib>

constexpr double DEFAULT_RE0 = -1.0;
constexpr double DEFAULT_RE1 = 1.0;
constexpr double DEFAULT_IM0 = -1.0;
constexpr double DEFAULT_IM1 = 1.0;
constexpr int ITER_LIMIT = 255;
constexpr int FPS_GOAL = 60;
constexpr int BG_CALC_MAX_ROWS_PER_FRAME = 300;
constexpr int SMALLEST_VALID_ZOOM_BOX_SIZE = 10;
constexpr int TRACER_COUNT = 10;

const Color white(255,255,255);
const Color black(0,0,0);
const Color red(255,0,0);
const Color green(0,255,0);
const Color blue(0,0,255);

inline double trf_coords(double cur, double a_lo, double a_hi, double b_lo, double b_hi)
{
  return static_cast<double>(cur-a_lo)/(a_hi-a_lo)*(b_hi-b_lo)+b_lo;
}

class ComplexIter
{
public:
  ComplexIter(double z_re, double z_im, double c_re, double c_im):z_n(z_re,z_im), c(c_re,c_im){};
  
  void set_c( double c_re, double c_im)
  {
    z_n = std::complex<double>(0,0);
    c=std::complex<double>(c_re,c_im);
  }
  
  ComplexIter& next()
  {
    z_n = z_n * z_n + c;
    return *this;
  }
  
  double re(){return std::real(z_n);}
  
  double im(){ return std::imag(z_n);}
 
  double abs() { return std::abs(z_n);};
private:
  std::complex<double> c;
  std::complex<double> z_n;
};

struct ScreenDimensions
{
  ScreenDimensions(int width, int height) : width(width), height(height){}
  int width;
  int height;
};

struct ComplexDimensions
{
  ComplexDimensions(
    int re0 = DEFAULT_RE0,
    int im0=DEFAULT_IM0, 
    int re1=DEFAULT_RE1,
    int im1=DEFAULT_IM1,
    int iter_lim=ITER_LIMIT) : 
    re0(re0),im0(im0), re1(re1),im1(im1),iter_lim(iter_lim){}
  double re0;
  double im0;
  double re1;
  double im1;
  int iter_lim;
};

struct Selbox
{
public:
  Selbox() :active(false){}
  
  bool active;
  
  Rect rect;
};

struct Tracer
{
public:
  Tracer(int tracer_count=TRACER_COUNT) : active(false),tracer_count(tracer_count){}

  int tracer_count;
  
  bool active;
  
  double c_re;
  
  double c_im;
  
  std::vector<Vec2D> points;
};

struct StaticTex
{
  StaticTex(Renderer& renderer, Uint32 pixel_format, int w, int h) : 
  w(w), h(h), texture(renderer, pixel_format,SDL_TEXTUREACCESS_STATIC, w, h){}
  int w;
  int h;
  Texture texture;
};

class PassiveDataRepo
{
  
public:
  PassiveDataRepo(Renderer& renderer,int w, int h, Uint32 bg_format):
  screen_dims(w,h), cx_dims(), tracer(),bg_tex(renderer,bg_format,w,h)
  {}
  
  ~PassiveDataRepo() {}

  PassiveDataRepo(const PassiveDataRepo&) = delete;
  
  PassiveDataRepo& operator=(const PassiveDataRepo&) = delete;
  
  PassiveDataRepo(PassiveDataRepo&&) =delete;
  
  PassiveDataRepo& operator=(PassiveDataRepo&&) = delete;

public:

  ScreenDimensions& get_screen_dims(){ return screen_dims;}
  
  ComplexDimensions& get_cx_dims(){ return cx_dims; }
  
  Tracer& get_tracer(){ return tracer;}
  
  Selbox& get_selbox() { return selbox;} 
  
  StaticTex& get_bg_tex(){ return bg_tex;}
private:
  ScreenDimensions screen_dims;
  ComplexDimensions cx_dims;
  Tracer tracer;
  Selbox selbox;
  StaticTex bg_tex;
};

struct BoxSelectionEvent
{
  Rect rect;
};

struct RecomputeBackgroundEvent
{
  RecomputeBackgroundEvent() : compute_whole_in_one_frame(false){}
  bool compute_whole_in_one_frame; /* currently unused */
};

class EventHandlerImpl : public EventHandler
{
public:
  EventHandlerImpl() : EventHandler() {}
  
  virtual ~EventHandlerImpl(){}
  
  EventHandlerImpl(const EventHandlerImpl&) = delete;
  
  EventHandlerImpl& operator=(const EventHandlerImpl&) = delete;
  
  EventHandlerImpl(EventHandlerImpl&&) =delete;
  
  EventHandlerImpl& operator=(EventHandlerImpl&&) = delete;
  
public:
  Signal<BoxSelectionEvent> sig_boxselection;
  
  Signal<RecomputeBackgroundEvent> sig_recompute_bg;
};


class AppService{
public:

  AppService(){}
  
  virtual ~AppService(){}
  
  AppService(const AppService&) = delete;
  
  AppService& operator=(const AppService&) = delete;
  
  AppService(AppService&&) =delete;
  
  AppService& operator=(AppService&&) = delete;
  
public:
  
  virtual void configure(EventHandlerImpl& evts) = 0;
  
  virtual void update(const FPSClock& clock, PassiveDataRepo& repo, EventHandlerImpl& evts) = 0;
};


class SelectionService : public AppService
{
public:

  SelectionService() : AppService(), active(false){}
  
  ~SelectionService() = default;

  SelectionService(const SelectionService&) = delete;
  
  SelectionService& operator=(const SelectionService&) = delete;
  
  SelectionService(SelectionService&&) =delete;
  
  SelectionService& operator=(SelectionService&&) = delete;
  
public:

  virtual void configure(EventHandlerImpl& evts)
  {
    evts.sig_mousebutton_up.connect(Simple::slot (*this, &SelectionService::on_mb_up));
    evts.sig_mousebutton_down.connect(Simple::slot (*this, &SelectionService::on_mb_down));
    evts.sig_mousemotion.connect(Simple::slot (*this, &SelectionService::on_mouse_moved));
  }
  
  virtual void update(const FPSClock& clock, PassiveDataRepo& repo, EventHandlerImpl& evts)
  {
    Selbox& selbox = repo.get_selbox();
    if(active){
      ScreenDimensions& sd = repo.get_screen_dims();
      double w_mult = static_cast<double>(std::abs(x1-x0))/sd.width;
      double h_mult = static_cast<double>(std::abs(y1-y0))/sd.height;
      double mult = std::max(w_mult,h_mult);
      x1=(x1<x0) ? x0-mult*sd.width : x0+mult*sd.width;
      y1=(y1<y0) ? y0-mult*sd.height : y0+mult*sd.height;
      if((x1<0||x1>sd.width) || (y1<0||y1>sd.height)){
        mult = std::min(w_mult,h_mult);
        x1=(x1<x0) ? x0-mult*sd.width : x0+mult*sd.width;
        y1=(y1<y0) ? y0-mult*sd.height : y0+mult*sd.height;
      }
      Rect newr = Rect::From_Corner_Points(x0,y0,x1,y1);
      if(selbox.active){
        if(selbox.rect!=newr){
          selbox.rect = std::move(newr);
        }
      }else{
        selbox.rect =  std::move(newr);
        selbox.active = true;
      }
    }else{
      if(selbox.active){
        selbox.active = false;
        if(
          selbox.rect.w>SMALLEST_VALID_ZOOM_BOX_SIZE &&
          selbox.rect.h>SMALLEST_VALID_ZOOM_BOX_SIZE)
          {
                BoxSelectionEvent e{.rect=selbox.rect};
        evts.sig_boxselection.emit(e);
          }
    
      }
    }
  }
  
  void on_mouse_moved(const SDL_MouseMotionEvent& evt)
  {
    if(active){
      x1=evt.x;
      y1=evt.y;
    }
  }
  
  void on_mb_up(const SDL_MouseButtonEvent& evt)
  { 
    if(evt.button==SDL_BUTTON_LEFT){
      if(active){
        active=false;
      }
      return;
    }
  }
  
  void on_mb_down(const SDL_MouseButtonEvent& evt)
  { 
    if(evt.button==SDL_BUTTON_LEFT){
      if(!active){
        x0 = x1 = evt.x;
        y0 = y1 = evt.y;
        active=true;
      }
      return;
    }
  }
private:
 
private:
  bool active;
  int x0;
  int y0;
  int x1;
  int y1;
};

class TracerService : public AppService
{
public:
  TracerService() : AppService(), active(false){}
  
  ~TracerService() = default;

  TracerService(const TracerService&) = delete;
  
  TracerService& operator=(const TracerService&) = delete;
  
  TracerService(TracerService&&) =delete;
  
  TracerService& operator=(TracerService&&) = delete;

public:
  
  virtual void configure(EventHandlerImpl& evts)
  {
    evts.sig_mousebutton_up.connect(Simple::slot (*this, &TracerService::on_mb_up));
  }
  
  virtual void update(const FPSClock& clock, PassiveDataRepo& repo, EventHandlerImpl& evts)
  {
    Tracer& tracer = repo.get_tracer();
    if(active){
      if(!tracer.active){
        tracer.active=true;
        recalculate(tracer, repo.get_screen_dims(),repo.get_cx_dims());
      }
    }else{
      tracer.active=false;
    }
  }
  
  void on_mb_up(const SDL_MouseButtonEvent& evt)
  { 
    if(evt.button==SDL_BUTTON_RIGHT){
      if(active)return;
      screen_pick_x = evt.x;
      screen_pick_y = evt.y;
      active=true;
      return;
    }else if(evt.button==SDL_BUTTON_LEFT){
      if(active)active=false;
    }
  }
  
private:
  void recalculate(Tracer& tracer,ScreenDimensions& screen_dims, ComplexDimensions& cx_dims)
  {
    tracer.points.clear();
    const double c_re = trf_coords(screen_pick_x,0,screen_dims.width,cx_dims.re0,cx_dims.re1);
    const double c_im = trf_coords(screen_pick_y,0,screen_dims.height,cx_dims.im0,cx_dims.im1);
    ComplexIter it(0,0,c_re,c_im);
    int x,y;
    for(int i = 0; i<tracer.tracer_count; ++i)
    {
      it.next();
      x = static_cast<int>(trf_coords(it.re(),cx_dims.re0,cx_dims.re1,0,screen_dims.width));
      y = static_cast<int>(trf_coords(it.im(),cx_dims.im0,cx_dims.im1,0,screen_dims.height));
      tracer.points.push_back(std::move(Vec2D(x,y)));
    }
  }
private:
  
  bool active;
  
  int screen_pick_x;
  
  int screen_pick_y;
};


class ComplexBoundsService : public AppService
{
public:
  ComplexBoundsService() :  AppService(), recalc_limits(false)
  {
  }
  
  virtual ~ComplexBoundsService(){}

  ComplexBoundsService(const ComplexBoundsService&) = delete;
  
  ComplexBoundsService& operator=(const ComplexBoundsService&) = delete;
  
  ComplexBoundsService(ComplexBoundsService&&) = delete;
  
  ComplexBoundsService& operator=(ComplexBoundsService&&) = delete;
  
public:

  virtual void configure(EventHandlerImpl& evts) override{
    evts.sig_boxselection.connect(Simple::slot (*this, &ComplexBoundsService::on_boxsel_ended));
    evts.sig_mousebutton_down.connect(Simple::slot (*this, &ComplexBoundsService::on_mb_down));
  }
  
  virtual void update(const FPSClock& clock, PassiveDataRepo& repo, EventHandlerImpl& evts)override
  {
    if(recalc_limits){
      recalc_cx_dims(repo.get_screen_dims(),repo.get_cx_dims());
      evts.sig_recompute_bg.emit(RecomputeBackgroundEvent());
      recalc_limits = false;
    }
  }

  void on_mb_down(const SDL_MouseButtonEvent& e)
  {
    if(e.button!=SDL_BUTTON_MIDDLE)return;
    x0=-1;
    y0=-1;
    x1=-1;
    y1=-1;
    recalc_limits = true;
  }
  
  void on_boxsel_ended(const BoxSelectionEvent& e)
  {
    x0=e.rect.x;
    y0=e.rect.y;
    x1=e.rect.x+e.rect.w;
    y1=e.rect.y+e.rect.h;
    recalc_limits = true;
  }
private:
  
  void recalc_cx_dims(const ScreenDimensions& scr, ComplexDimensions& cx_dims)
  {
    if(x0<0||y0<0||x1<0||y1<0){
      cx_dims.re0=DEFAULT_RE0;
      cx_dims.re1=DEFAULT_RE1;
      cx_dims.im0=DEFAULT_IM0;
      cx_dims.im1=DEFAULT_IM1;
    }else{
      double re0 = trf_coords(std::min(x0,x1),0,scr.width,cx_dims.re0,cx_dims.re1);
      double re1 = trf_coords(std::max(x0,x1),0,scr.width,cx_dims.re0,cx_dims.re1);
      double im0 = trf_coords(std::min(y0,y1),0,scr.height,cx_dims.im0,cx_dims.im1);
      double im1 = trf_coords(std::max(y0,y1),0,scr.height,cx_dims.im0,cx_dims.im1);
      cx_dims.re0=re0;
      cx_dims.re1=re1;
      cx_dims.im0=im0;
      cx_dims.im1=im1;
    }
  }
private:
  bool recalc_limits;
  int x0;
  int y0;
  int x1;
  int y1;
};

class MandelUpdaterService : public AppService
{
public:
  MandelUpdaterService(const StaticTex& bg_tex) : 
  AppService(), recompute_requested(false), bg_calc_running(false),bg_calc_y(0)
  {
    argb_buffer = (uint32_t*) malloc(bg_tex.w*bg_tex.h*sizeof(uint32_t));
    if( argb_buffer == nullptr ){throw std::runtime_error("Failed to allocate bg buffer");}
  }
  
  virtual ~MandelUpdaterService(){if(argb_buffer!=nullptr)free(argb_buffer);}

  MandelUpdaterService(const MandelUpdaterService&) = delete;
  
  MandelUpdaterService& operator=(const MandelUpdaterService&) = delete;
  
  MandelUpdaterService(MandelUpdaterService&&) = delete;
  
  MandelUpdaterService& operator=(MandelUpdaterService&&) = delete;
  
public:

  virtual void configure(EventHandlerImpl& evts) override{
    evts.sig_recompute_bg.connect(Simple::slot (*this, &MandelUpdaterService::on_recompute_request));
  }
  
  void on_recompute_request(const RecomputeBackgroundEvent& e)
  {
    recompute_requested = true;
  }

  virtual void update(const FPSClock& clock, PassiveDataRepo& repo, EventHandlerImpl& evts)override
  {
    if(recompute_requested){
      bg_calc_running = true;
      bg_calc_y = 0;
      recompute_requested=false;
    }
    if(!bg_calc_running){return;};  
    compute_bg(clock, repo.get_screen_dims(), repo.get_cx_dims(), repo.get_bg_tex());
  }
  
private:
  
  void compute_bg(const FPSClock& clock, const ScreenDimensions& scr, const ComplexDimensions& cx_dims, StaticTex& bg_tex)
  {
    double d_re = ( cx_dims.re1 - cx_dims.re0 ) / bg_tex.w;
    double d_im = ( cx_dims.im1 - cx_dims.im0 ) / bg_tex.h;
    double c_re,c_im;
    int iter;
    const int limit = std::min(bg_tex.h,bg_calc_y+BG_CALC_MAX_ROWS_PER_FRAME);
    ComplexIter cit(0,0,0,0);
    int y = bg_calc_y;
    PixelFormat format(bg_tex.texture);
    int head = y*bg_tex.w;
    while(y<limit && clock.remaining()>0){
      c_im = cx_dims.im0 + y * d_im;
      for ( int x = 0; x < bg_tex.w; ++x ){
        c_re = cx_dims.re0 + x * d_re;
        cit.set_c(c_re,c_im);
        iter = 0;
        while ( cit.abs() < 4 && iter < cx_dims.iter_lim ){
          cit.next();
          ++iter;
        }
        argb_buffer[ y*bg_tex.w+x] = format.map_rgba(iter%255,(iter*iter)%255,0,0xff);
      }
      y++;
    }
    if(head!=y*bg_tex.w){
      Rect subarea(0,bg_calc_y,bg_tex.w,y-bg_calc_y);
      bg_tex.texture.update_subarea(subarea,(argb_buffer+head),bg_tex.w*4);
    }
    bg_calc_y = y;
    if(bg_calc_y>=bg_tex.h)bg_calc_running=false;
  }
  
private:
  uint32_t* argb_buffer;
  bool recompute_requested;
  bool bg_calc_running;
  int bg_calc_y;
  int x0;
  int y0;
  int x1;
  int y1;
};


class ScreenRendererService : public AppService
{
public:
  ScreenRendererService(Renderer& renderer) : AppService(), renderer(renderer)
  { }
  
  virtual ~ScreenRendererService(){}

  ScreenRendererService(const ScreenRendererService&) = delete;
  
  ScreenRendererService& operator=(const ScreenRendererService&) = delete;
  
  ScreenRendererService(ScreenRendererService&&) = delete;
  
  ScreenRendererService& operator=(ScreenRendererService&&) = delete;
  
public:

  virtual void configure(EventHandlerImpl& evts) override{}
  
  virtual void update(const FPSClock& clock, PassiveDataRepo& repo, EventHandlerImpl& evts)override
  {
    renderer.copy_fulltex_to_fullscreen(repo.get_bg_tex().texture);
    Tracer& tracer = repo.get_tracer();
    if(tracer.active){
     renderer.set_color(red).draw_lines(tracer.points);
    }
    Selbox& selbox = repo.get_selbox();
    if(selbox.active){
     renderer.set_color(green).draw_rect(selbox.rect);
    }
    renderer.present();
  }
private:

  Renderer& renderer;
};


class App
{
  
public:
  App(const std::string& title, int width, int height) : 
  window(title,width,height), 
  renderer(window),
  clock(FPS_GOAL),
  repo(renderer,width,height,SDL_PIXELFORMAT_ARGB8888),
  srv_sel(),
  srv_tracer(),
  srv_mandel(repo.get_bg_tex()),
  srv_screen_renderer(renderer),
  running(false)
  {
    
  }
  
  virtual ~App()
  {
    
  }
  
  App(const App&) = delete;
  
  App& operator=(const App&) = delete;
  
  App( App&&) = delete;
  
  App& operator=( App&&) = delete;

public:

  void configure()
  {    
    evts.sig_quit.connect(Simple::slot (*this, &App::stop));
    srv_sel.configure(evts);
    srv_tracer.configure(evts);
    srv_cx_bounds.configure(evts);
    srv_mandel.configure(evts);
    srv_screen_renderer.configure(evts); 
  }

  void run()
  {
    if(running)return;
    running=true;
    const Uint32 FPS = 60;
    const Uint32 frame_delay = 1000/FPS;
    Uint32 frame_time;
    clock.restart();
    evts.sig_recompute_bg.emit(RecomputeBackgroundEvent());
    while(running){
      clock.restart();
      while(evts.poll()){}
      update();
      frame_time = clock.restart();
      if(frame_delay>frame_time)SDL_Delay(frame_delay-frame_time);
    }
  }
  
  void stop(const SDL_QuitEvent& e){running = false;}
  
private:
  void update()
  {
    srv_sel.update(clock,repo,evts);
    srv_tracer.update(clock,repo,evts);
    srv_cx_bounds.update(clock,repo,evts);
    srv_mandel.update(clock,repo,evts);
    srv_screen_renderer.update(clock,repo,evts);
  }
  
private:
  Window window;
  
  Renderer renderer;
  
  FPSClock clock;
  
  EventHandlerImpl evts;
  
  PassiveDataRepo repo;
  
  SelectionService srv_sel;
  
  TracerService srv_tracer;
  
  ComplexBoundsService srv_cx_bounds;
  
  MandelUpdaterService srv_mandel;
  
  ScreenRendererService srv_screen_renderer;
  bool running;

};

int main()
{ 
  try{
    SDL_Guard guard(SDL_INIT_EVERYTHING);
    App app("Mandel",300,300);
    app.configure();
    app.run();
  }catch(const std::runtime_error& ex){
    std::cout << ex.what() << std::endl;
  }catch(...){
    std::cout << "Unknown error! Exiting " << std::endl;
  }
  return 0;
}