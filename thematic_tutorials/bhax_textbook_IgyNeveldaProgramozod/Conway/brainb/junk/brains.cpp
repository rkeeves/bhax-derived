// [COMPILE]
// -Wl,-subsystem,windows -lmingw32 -lSDL2main -lSDL2
#include "sdl_wrapper.hpp"
#include <cstdlib>
#include <iterator>
#include <iostream>
#include <algorithm>
#include <array>

constexpr int FPS_GOAL = 60;

const Color white(255,255,255);
const Color black(0,0,0);
const Color red(255,0,0);
const Color green(0,255,0);
const Color blue(0,0,255);


class EventHandlerImpl : public EventHandler
{
public:
  EventHandlerImpl() : EventHandler() {}
  
  virtual ~EventHandlerImpl(){}
  
  EventHandlerImpl(const EventHandlerImpl&) = delete;
  
  EventHandlerImpl& operator=(const EventHandlerImpl&) = delete;
  
  EventHandlerImpl(EventHandlerImpl&&) =delete;
  
  EventHandlerImpl& operator=(EventHandlerImpl&&) = delete;
  
};


class CellGrid
{
public:
  CellGrid() = delete;
  
  CellGrid(int w, int h)
  : w(w), h(h), cur(w*h,false),last(w*h,false)
  {
    
  }
  
public:
  std::vector<bool>& get_cur()
  {
    return cur;
  }
  
  bool set_cell(int row, int col, bool v)
  {
    if(in_rng(row,col)){
      int i = to_idx(row,col);
      bool t = cur[i];
      cur[i] = v;
      return t;
    }else{
      return false;
    }
   
  }
  
  void beacon(int row, int col)
  {
    set_cell(row+0,0+col, true);
    set_cell(row+1,0+col, true);
    set_cell(row+0,1+col, true);
    set_cell(row+3,2+col, true);
    set_cell(row+2,3+col, true);
    set_cell(row+3,3+col, true);
    
  }
  
  void glider_cannon(int row, int col)
  {
    set_cell(row+5,1+col, true);
    set_cell(row+6,1+col, true);
    set_cell(row+5,2+col, true);
    set_cell(row+6,2+col, true);
    set_cell(row+5,11+col, true);
    set_cell(row+6,11+col, true);
    set_cell(row+7,11+col, true);
    set_cell(row+4,12+col, true);
    set_cell(row+8,12+col, true);
    set_cell(row+3,13+col, true);
    set_cell(row+9,13+col, true);
    set_cell(row+3,14+col, true);
    set_cell(row+9,14+col, true);
    set_cell(row+6,15+col, true);
    set_cell(row+4,16+col, true);
    set_cell(row+8,16+col, true);
    set_cell(row+5,17+col, true);
    set_cell(row+6,17+col, true);
    set_cell(row+7,17+col, true);
    set_cell(row+6,18+col, true);
    set_cell(row+3,21+col, true);
    set_cell(row+4,21+col, true);
    set_cell(row+5,21+col, true);
    set_cell(row+3,22+col, true);
    set_cell(row+4,22+col, true);
    set_cell(row+5,22+col, true);
    set_cell(row+2,23+col, true);
    set_cell(row+6,23+col, true);
    set_cell(row+1,25+col, true);
    set_cell(row+2,25+col, true);
    set_cell(row+6,25+col, true);
    set_cell(row+7,25+col, true);
    set_cell(row+3,35+col, true);
    set_cell(row+4,35+col, true);
    set_cell(row+3,36+col, true);
    set_cell(row+4,36+col, true);

  }
  
  void update()
  {
    int n;
    for(int i = 0; i<h; i++){
      for(int j = 0; j<w; j++){
        n = count_alive_neighbors(i,j);
        if(last[to_idx(i,j)]){
          if(n==2 || n==3){
            cur[to_idx(i,j)] = true;
          }else{
            cur[to_idx(i,j)] = false;
          }
        }else{
          if(n==3){
            cur[to_idx(i,j)] = true;
          }else{
            cur[to_idx(i,j)] = false;
          }       
        } 
      }
    }
  }
    
  void save_cur()
  {
    last = cur;
  }
  
private:

  inline int to_idx(int row, int col)
  {
    return row*w+col;
  }
  
  inline bool in_rng(int row, int col)
  {
    return (0<row && row<h && col<w && 0<col);
  }
  
  bool get_last_cell(int row, int col)
  {
    if(in_rng(row,col)){
      return last[to_idx(row,col)];
    }else{
      return false;
    }
  }
  
  int count_alive_neighbors(int row, int col)
  {
    int n = 0;
    for(int i=-1; i<2; i++){
      for(int j=-1; j<2; j++){
        if(j==0&&i==0) continue;
        if(in_rng(row+i,col+j)){
          if(last[to_idx(row+i,col+j)]){
            n++;
          }
        }
      }
    }
    
    return n;
  }
  
  

private:
  int w;
  int h;
  std::vector<bool> last;
  std::vector<bool> cur;
};

class App
{
  
public:
  App(const std::string& title, int width, int height, int scale) : 
  width(width),
  height(height),
  scale(scale),
  window(title,width*scale,height*scale), 
  renderer(window),
  clock(FPS_GOAL),
  grid(width,height),
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
    setup_game_world();
  }

  void run()
  {
    if(running)return;
    running=true;
    const Uint32 frame_delay = 1000/FPS_GOAL;
    Uint32 frame_time;
    clock.restart();
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

  void setup_game_world()
  {
    grid.glider_cannon(height/4,width/4);
    grid.save_cur();

  }
  
  void update()
  {
    render();
   // grid.update();
    //grid.save_cur();
    
  }

  void render()
  {
    renderer.set_color(white);
    renderer.clear();
    renderer.set_color(black);
    Rect r(0,0,scale,scale);
    const auto &v = grid.get_cur();
    for(int i=0; i<v.size();++i){
      if(v[i]){
        r.x=i%width*scale;
        r.y=i/width*scale;
        renderer.fill_rect(r);
      }
    }
    renderer.present();
  }
  
private:
  int width;
  
  int height;
  
  int scale;
  
  Window window;
  
  Renderer renderer;
  
  FPSClock clock;
  
  EventHandlerImpl evts;
  
  CellGrid grid;
  
  bool running;

};

int main()
{ 
  try{
    SDL_Guard guard(SDL_INIT_EVERYTHING);
    App app("Game Of Life",100,100, 4);
    app.configure();
    app.run();
  }catch(const std::runtime_error& ex){
    std::cout << ex.what() << std::endl;
  }catch(...){
    std::cout << "Unknown error! Exiting " << std::endl;
  }
  return 0;
}