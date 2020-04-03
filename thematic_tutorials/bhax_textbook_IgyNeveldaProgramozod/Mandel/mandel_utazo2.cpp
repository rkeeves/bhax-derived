// [COMMENT]
// Didnt want to overcomplicate it with event bus, therefore it is quite a mess.
//
// [COMPILE]
// -Wl,-subsystem,windows -lmingw32 -lSDL2main -lSDL2
//
// [USAGE]
//  +ZOOM
//    Press and keep pressing left button to drag selection box.
//    When left is released the zoom recalculation will be initiated.
//  +TRACE
//    Release the right mouse button.
//   The app will draw lines representing the results of the iteration steps.
//  +RESET
//    Release the middle mouse button.
//    The app will recalculate everything to defaults.
//    (e.g. it will take you back to the image you have seen first)
//
#include <iostream>
#include <string>
#include <sstream>
#define SDL_MAIN_HANDLED 
#include <sdl2/SDL.h>
#include <stdexcept>
#include <complex>
#include <cstdlib>
//[LOGGING]
// Uncomment below for logging
// #define CUSTOM_DEBUG_ZOOM
// #define CUSTOM_DEBUG_TRACER

constexpr int WIDTH = 300;
constexpr int HEIGHT = 300;
constexpr int RATIO = WIDTH/HEIGHT;
constexpr int PIXEL_COUNT = WIDTH*HEIGHT;
constexpr int TRACER_COUNT = 10;
constexpr int ITER_LIMIT = 255;
constexpr int FRAMES_PER_SECOND_GOAL = 60;
constexpr int BG_CALC_MAX_PIXELS_PER_FRAME = 30000;
constexpr int BG_CALC_MAX_ROWS_PER_FRAME = BG_CALC_MAX_PIXELS_PER_FRAME/HEIGHT;
constexpr double DEFAULT_RE0 = -1.0;
constexpr double DEFAULT_RE1 = 1.0;
constexpr double DEFAULT_IM0 = -1.0;
constexpr double DEFAULT_IM1 = 1.0;

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

  
class FPSBasedDelayer
{
public:
  FPSBasedDelayer(const int FPS): frame_delay(1000/FPS),frame_start(0){}
  
  void on_frame_start() {frame_start = SDL_GetTicks();}
  
  int remaining_time() const
  {
     return frame_delay-(SDL_GetTicks() - frame_start);
  }
  
  void on_frame_stop()
  {
    int frame_time = SDL_GetTicks() - frame_start;
    if(frame_delay > frame_time){
      SDL_Delay(frame_delay-frame_time);
    }
  }
private:
  const int frame_delay;
  Uint32 frame_start;
};


inline double trf_coords(double cur, double a_lo, double a_hi, double b_lo, double b_hi)
{
  return static_cast<double>(cur-a_lo)/(a_hi-a_lo)*(b_hi-b_lo)+b_lo;
}

struct Dimensions
{
  Dimensions() : re0(DEFAULT_RE0),im0(DEFAULT_IM0), re1(DEFAULT_RE1),im1(DEFAULT_IM1),iter_lim(ITER_LIMIT){}
  double re0;
  double im0;
  double re1;
  double im1;
  int iter_lim;
};

enum class MouseEventType{MouseMoved,LeftPressed,LeftReleased,MiddlePressed,MiddleReleased,RightPressed,RightReleased,Unhandled};
struct MouseEvent
{
  MouseEventType type;
  int x;
  int y;
};

struct SelectionBox
{
  SelectionBox() : active(false),active_last(false){}
  bool active;
  bool active_last;
  int x0;
  int y0;
  int x1;
  int y1;
};

struct Tracer
{
  Tracer() : active(false){}
  bool active;
  SDL_Point points[TRACER_COUNT];
};

class App
{
public:
  App(std::string title) : 
  running(false), title(title), window(NULL), renderer(NULL), bg_tex(NULL), pixel_format(NULL), bg_buf(NULL), recalc_bg(true), bg_calc_running(false)
  { }
  
  ~App()
  { }
private:
  App(const App&);
  App& operator=(const App&);
  App( App&&);
  App& operator=( App&&);
public:
  void init();
  
  bool is_running() const {return running;};
  
  void update(const FPSBasedDelayer& fps);
  
  void handle_events();

  void cleanup();
private:
  void handle_event(const SDL_Event&);
   
  void update_selection();

  void update_dimensions();
  
  void update_tracer();
  
  void calculate_tracer_points(double re, double im);
  
  void update_mandel(const FPSBasedDelayer& fps);
  
  void clear();
  
  void render();
  
private:
  bool running;
  std::string title;
  SDL_Window *window;
  SDL_Renderer* renderer;
  SDL_Texture* bg_tex;
  SDL_PixelFormat* pixel_format;
  uint32_t *bg_buf;
  bool recalc_bg;
  bool bg_calc_running;
  int bg_calc_y;
  bool block_event_polling;
  Dimensions dim;
  MouseEvent mevt;
  SelectionBox box;
  Tracer tracer;
};

void App::init()
{
  if(SDL_Init(SDL_INIT_EVERYTHING) != 0){
    cleanup();
    std::stringstream ss;
    ss << "SDL_Init failed " << SDL_GetError();
    throw std::runtime_error(ss.str());
  }
  window = SDL_CreateWindow( title.c_str(), SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, WIDTH, HEIGHT, SDL_WINDOW_SHOWN );
  if( window == NULL ){
    cleanup();
    std::stringstream ss;
    ss << "Window could not be created! SDL_Error: " << SDL_GetError();
    throw std::runtime_error(ss.str());
  }
  renderer = SDL_CreateRenderer(window,1,0);
  if( renderer == NULL ){
    cleanup();
    std::stringstream ss;
    ss << "Renderer could not be created! SDL_Error: " << SDL_GetError() << std::endl;
    throw std::runtime_error(ss.str());
  }
  bg_tex = SDL_CreateTexture( renderer, SDL_PIXELFORMAT_RGBA8888, SDL_TEXTUREACCESS_STREAMING, WIDTH, HEIGHT );
  if( bg_tex == NULL ){
    cleanup();
    std::stringstream ss;
    ss <<  "Unable to create bg texture! SDL Error: " << SDL_GetError() << std::endl;
    throw std::runtime_error(ss.str());
  }
  bg_buf = (uint32_t*) malloc(WIDTH*HEIGHT*sizeof(uint32_t));
  if( bg_buf == NULL ){
    cleanup();
    std::stringstream ss;
    ss <<  "Unable to allocate bg buffer!"<< std::endl;
    throw std::runtime_error(ss.str());
  }
  SDL_SetWindowPosition(window, SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED);
  pixel_format = SDL_AllocFormat(SDL_PIXELFORMAT_RGBA8888);
  running = true;
}

void App::handle_events()
{
  block_event_polling = false;
  SDL_Event evt;
  while (SDL_PollEvent(&evt)) 
  {
    if(evt.type==SDL_QUIT){
      running = false;
      break;
    }else{
      if(!block_event_polling){
        handle_event(evt);
      }
    }
  }     
}

void App::handle_event(const SDL_Event& evt)
{
  mevt.type=MouseEventType::Unhandled;
  if(evt.type==SDL_MOUSEMOTION){
    mevt.x= evt.button.x;
    mevt.y= evt.button.y;
    mevt.type=MouseEventType::MouseMoved;
    block_event_polling = true;
  }else if(evt.type==SDL_MOUSEBUTTONDOWN ){
    mevt.x= evt.button.x;
    mevt.y= evt.button.y;
    if(evt.button.button == SDL_BUTTON_LEFT){
      mevt.type=MouseEventType::LeftPressed;
    }else if(evt.button.button == SDL_BUTTON_RIGHT){
      mevt.type=MouseEventType::RightPressed;
    }else if(evt.button.button == SDL_BUTTON_MIDDLE){
      mevt.type=MouseEventType::MiddlePressed;
    }else{
      return; // unhandled mouse button
    }
    block_event_polling = true;
  }else if(evt.type==SDL_MOUSEBUTTONUP){
    mevt.x= evt.button.x;
    mevt.y= evt.button.y;
    if(evt.button.button == SDL_BUTTON_LEFT){
      mevt.type=MouseEventType::LeftReleased;
    }else if(evt.button.button == SDL_BUTTON_RIGHT){
      mevt.type=MouseEventType::RightReleased;
    }else if(evt.button.button == SDL_BUTTON_MIDDLE){
      mevt.type=MouseEventType::MiddleReleased;
    }else{
      return; // unhandled mouse button
    }
     block_event_polling = true;
  }
}

void App::update(const FPSBasedDelayer& fps)
{
  if(running == false)return;
  update_selection();
  update_dimensions();
  update_tracer();
  update_mandel(fps);
  render();
}

void App::update_selection()
{
  box.active_last = box.active;
  if(box.active){
    if(mevt.type==MouseEventType::MouseMoved){
      double w_mult = static_cast<double>(std::abs(mevt.x-box.x0))/WIDTH;
      double h_mult = static_cast<double>(std::abs(mevt.y-box.y0))/HEIGHT;
      double mult = std::max(w_mult,h_mult);
      box.x1=mult*WIDTH+box.x0;
      box.y1=mult*HEIGHT+box.y0;
    }else if( mevt.type==MouseEventType::LeftReleased ||
              mevt.type==MouseEventType::MiddleReleased){
      box.active=false;
    }
  }else{
    if(mevt.type==MouseEventType::LeftPressed){
      box.x0=box.x1=mevt.x;
      box.y0=box.y1=mevt.y;
      box.active=true;
    }
  }
}

void App::update_dimensions()
{
  bool selection_ended = !box.active&&box.active_last;
  if(mevt.type==MouseEventType::MiddleReleased){
    dim.re0=DEFAULT_RE0;
    dim.re1=DEFAULT_RE1;
    dim.im0=DEFAULT_IM0;
    dim.im1=DEFAULT_IM1;
    dim.iter_lim=ITER_LIMIT;
    #ifdef CUSTOM_DEBUG_ZOOM
      std::cout << "Reset zoom to original re["<<dim.re0<<","<<dim.re1<<"] im["<<dim.im0<<","<<dim.im1<<"]"<< std::endl;
    #endif
    recalc_bg = true;
  }else if(selection_ended){
    if(box.x0==box.x1||box.y0==box.y1)return;
    #ifdef CUSTOM_DEBUG_ZOOM
      std::cout << "Selected points from ("<<box.x0<<","<<box.y0<<") to ("<<box.x1<<","<<box.y1<<")"<< std::endl;
      std::cout << "Zooming from re["<<dim.re0<<","<<dim.re1<<"] im["<<dim.im0<<","<<dim.im1<<"]"<< std::endl;
    #endif
    double re0 = trf_coords(std::min(box.x0,box.x1),0,WIDTH,dim.re0,dim.re1);
    double re1 = trf_coords(std::max(box.x0,box.x1),0,WIDTH,dim.re0,dim.re1);
    double im0 = trf_coords(std::min(box.y0,box.y1),0,HEIGHT,dim.im0,dim.im1);
    double im1 = trf_coords(std::max(box.y0,box.y1),0,HEIGHT,dim.im0,dim.im1);
    dim.re0=re0;
    dim.re1=re1;
    dim.im0=im0;
    dim.im1=im1;
    #ifdef CUSTOM_DEBUG_ZOOM
      std::cout << "Zooming to re["<<dim.re0<<","<<dim.re1<<"] im["<<dim.im0<<","<<dim.im1<<"]"<< std::endl;
    #endif
    recalc_bg = true;
  }
}

void App::update_tracer()
{
  if(tracer.active){
    bool selection_ended = !box.active&&box.active_last;
    if( selection_ended || 
        mevt.type==MouseEventType::LeftPressed || 
         mevt.type==MouseEventType::MiddleReleased){
      tracer.active=false;
      #ifdef CUSTOM_DEBUG_TRACER
      std::cout << "Tracing inactive"<< std::endl;
    #endif
    }
  }else{
     if(mevt.type==MouseEventType::RightPressed && !box.active){
      double re = trf_coords(mevt.x,0,WIDTH,dim.re0,dim.re1);
      double im = trf_coords(mevt.y,0,HEIGHT,dim.im0,dim.im1);
      #ifdef CUSTOM_DEBUG_TRACER
        std::cout << "Tracing activated ["<<re<<","<<im<<"]"<< std::endl;
      #endif
      calculate_tracer_points(re,im);
      tracer.active=true;
      
     }
  }
}

void App::calculate_tracer_points(double reC, double imC)
{
  ComplexIter it(0,0,reC,imC);
  for(int i = 0; i<TRACER_COUNT; ++i)
  {
    it.next();
    tracer.points[i].x= static_cast<int>(trf_coords(it.re(),dim.re0,dim.re1,0,WIDTH));
    tracer.points[i].y= static_cast<int>(trf_coords(it.im(),dim.im0,dim.im1,0,HEIGHT));
    #ifdef CUSTOM_DEBUG_TRACER
      std::cout<<"TracePoint["<<i<<"] Q("<<it.re()<<","<<it.im()<<") Screen("<<tracer.points[i].x<<","<<tracer.points[i].x<<")"<<std::endl;
    #endif
  }
}

void App::update_mandel(const FPSBasedDelayer& fps)
{
  if(recalc_bg){
    bg_calc_running = true;
    bg_calc_y = 0;
    recalc_bg = false;
  }
  if(!bg_calc_running){return;};
  double d_re = ( dim.re1 - dim.re0 ) / WIDTH;
  double d_im = ( dim.im1 - dim.im0 ) / HEIGHT;
  double c_re,c_im;
  int iter;
  const int limit = std::min(HEIGHT,bg_calc_y+BG_CALC_MAX_ROWS_PER_FRAME);
  ComplexIter cit(0,0,0,0);
  int y = bg_calc_y;
  while(y<limit && fps.remaining_time()>0){
    
    c_im = dim.im0 + y * d_im;
    for ( int x = 0; x < WIDTH; ++x ){
      c_re = dim.re0 + x * d_re;
      cit.set_c(c_re,c_im);
      iter = 0;
      while ( cit.abs() < 4 && iter < dim.iter_lim ){
        cit.next();
        ++iter;
      }
      bg_buf[y*WIDTH+x] = SDL_MapRGBA(pixel_format,iter%255,(iter*iter)%255,0,0xff);
    }
    y++;
  }
  
  SDL_Rect rect;
  rect.x=0;
  rect.y=bg_calc_y;
  rect.w=WIDTH;
  rect.h=y-bg_calc_y;
  SDL_UpdateTexture(bg_tex, NULL, bg_buf, WIDTH * sizeof (Uint32));
  bg_calc_y = y;
  if(bg_calc_y>=HEIGHT){bg_calc_running=false;}
}

void App::render()
{
  if(renderer==NULL)return;
  SDL_SetRenderDrawColor(renderer, 0, 0,0,255);
  SDL_RenderClear(renderer);
  SDL_RenderCopy(renderer, bg_tex, NULL, NULL);
  if(box.active){
    SDL_SetRenderDrawColor(renderer, 255, 0,0,255);
    SDL_RenderDrawLine(renderer,box.x0,box.y0,box.x1,box.y0);
    SDL_RenderDrawLine(renderer,box.x1,box.y0,box.x1,box.y1);
    SDL_RenderDrawLine(renderer,box.x1,box.y1,box.x0,box.y1);
    SDL_RenderDrawLine(renderer,box.x0,box.y1,box.x0,box.y0);
  }
  if(tracer.active){
    SDL_SetRenderDrawColor(renderer, 255, 0,255,255);
    SDL_RenderDrawLines(renderer,tracer.points,TRACER_COUNT);
  }
  SDL_RenderPresent(renderer);
} 

void App::cleanup()
{
  if(window!=NULL){  SDL_DestroyWindow(window);}
  if(renderer!=NULL){ SDL_DestroyRenderer(renderer);}
  if(bg_tex!=NULL){ SDL_DestroyTexture(bg_tex);}
  if(pixel_format!=NULL){free(pixel_format);}
  if(bg_buf!=NULL){ free(bg_buf);}
  SDL_Quit();
}

int main()
{ 
  App app("Mandel");
  try{
    app.init();
  }catch(const std::runtime_error& ex){
    std::cout << ex.what() << std::endl;
    return -1;
  }catch(...){
    std::cout << "Unknown error! Exiting " << std::endl;
    return -1;
  }
  FPSBasedDelayer fps_delayer(FRAMES_PER_SECOND_GOAL);
  while(app.is_running()){
    fps_delayer.on_frame_start();
    app.handle_events();
    app.update(fps_delayer);
    fps_delayer.on_frame_stop();
  }
  app.cleanup();
  return 0;
}