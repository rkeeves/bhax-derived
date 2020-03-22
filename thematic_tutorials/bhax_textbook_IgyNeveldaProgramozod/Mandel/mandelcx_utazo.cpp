// Uses SDL2, because of fun and the fact that png++ uses non standard strerror_r
// For compilation use
// -Wl,-subsystem,windows -lmingw32 -lSDL2main -lSDL2
// KAWOOOSH

#include <iostream>
#include <complex>
#define SDL_MAIN_HANDLED 
#include <sdl2/SDL.h>
#include <algorithm>    // std::max
typedef struct input_t{
  bool mbl;
  bool mbr;
  int x;
  int y;
  
  void update(SDL_Event &evt){
    if(evt.type==SDL_MOUSEBUTTONDOWN){
      x = evt.button.x;
      y = evt.button.y;
      if( evt.button.button == SDL_BUTTON_LEFT ){
        mbl = true;
      }else if( evt.button.button == SDL_BUTTON_RIGHT ){
        mbr = true;
      }
    }else if(evt.type==SDL_MOUSEBUTTONUP){
      x = evt.button.x;
      y = evt.button.y;
      if( evt.button.button == SDL_BUTTON_LEFT ){
        mbl = false;
      }else if( evt.button.button == SDL_BUTTON_RIGHT ){
        mbr = false;
      }
    }
  }
 
} MouseInput;



class MandelDraw
{
public:
  MandelDraw(int w,int h, int iter_lim,double a,double b,double c,double d)
  : w(w),h(h),iter_lim(iter_lim),a(a),b(b),c(c),d(d), redraw_needed(true), in_drag(false),
  da(a),db(b),dc(c),dd(d)
  { }
  
  void update(const MouseInput& inp)
  {
    if(in_drag){
      if(inp.mbl == false){
        in_drag = false;
        double box_x_lo = std::min(last_x,inp.x);
        double box_x_hi = std::max(last_x,inp.x);
        double box_y_lo = std::min(h-last_y,h-inp.y);
        double box_y_hi = std::max(h-last_y,h-inp.y);
        double na = trf(box_x_lo,w,a,b);
        double nb = trf(box_x_hi,w,a,b);
        double nc = trf(box_y_lo,h,c,d);
        double nd = trf(box_y_hi,h,c,d);
        std::cout<< "Dragbox [" << box_x_lo <<","<<box_y_lo<<"] and ["<< box_x_hi<<","<<box_y_hi<<"]"<<std::endl;
        std::cout<< "Cx  old [" << a <<","<<c<<"] and ["<< b<<","<<d<<"]"<<std::endl;
        std::cout<< "Cx  new [" << na <<","<<nc<<"] and ["<< nb<<","<<nd<<"]"<<std::endl;
        a = na;
        b = nb;
        c = nc;
        d = nd;
        redraw_needed = true;
      }
    }else{
      if(inp.mbl){
        in_drag = true;
        last_x = inp.x;
        last_y = inp.y;
      }else if(inp.mbr){
        a = da;
        b = db;
        c = dc;
        d = dd;
        std::cout<< "Reset   [" << a <<","<<c<<"] and ["<< b<<","<<d<<"]"<<std::endl;
        redraw_needed = true;
      }
    }
  }
  
  inline double trf(double x, double hi_0,double lo_1, double hi_1)
  {
    return (hi_0==0)?0.0:((x/hi_0)*(hi_1-lo_1)+lo_1);
  }

  
  void render(SDL_Renderer *renderer)
  {
    if(redraw_needed == false)return;
    redraw_needed = false;
    int szazalek = 0; // unused
    double dx = ( b - a ) / w;
    double dy = ( d - c ) / h;
    double reC, imC, reZ, imZ;
    int iteracio = 0;
    for ( int j = 0; j < h; ++j ){
      imC = d - j * dy;
      for ( int k = 0; k < w; ++k ){
        reC = a + k * dx;
        std::complex<double> c ( reC, imC );
        std::complex<double> z_n ( 0, 0 );
        iteracio = 0;
        while ( std::abs ( z_n ) < 4 && iteracio < iter_lim ){
          z_n = z_n * z_n + c;
          ++iteracio;
        }
        SDL_SetRenderDrawColor(renderer, iteracio%255, (iteracio*iteracio)%255,0, 0xff);
        SDL_RenderDrawPoint(renderer, k, j);
      }
    }
    SDL_RenderPresent(renderer);
  }
private:
  int w;
  int h;
  int iter_lim;
  double a;
  double b;
  double c;
  double d;
  bool redraw_needed;
  bool in_drag;
  int last_x;
  int last_y;
  double da;
  double db;
  double dc;
  double dd;
};

int main(int argc, char** argv)
{
  SDL_SetMainReady(); // just for check 
  int szelesseg = 300;
  int magassag = 300;
  int iteraciosHatar = 255;
  double a = -1;
  double b = 1;
  double c = -1;
  double d = 1;
  if ( argc == 1 ){
    // Use defaults
  }else if ( argc == 8 ){
        szelesseg = atoi ( argv[1] );
      magassag =  atoi ( argv[2] );
      iteraciosHatar =  atoi ( argv[3] );
      a = atof ( argv[4] );
      b = atof ( argv[5] );
      c = atof ( argv[6] );
      d = atof ( argv[7] );
  }else{
    std::cout << "Hasznalat: ./3.1.2 fajlnev szelesseg magassag n a b c d" << std::endl;
    return -1;
  }
  if(SDL_Init(SDL_INIT_VIDEO) != 0){
    std::cout << "SDL_Init failed" << std::endl;
    return -1;
  }
  SDL_Window *window = SDL_CreateWindow( "Mandel", SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, szelesseg, magassag, SDL_WINDOW_SHOWN );
  if( window == NULL )
  {
    std::cout << "Window could not be created! SDL_Error: " << SDL_GetError() << std::endl;
    SDL_Quit();
    return -1;
  }
  SDL_Renderer* renderer = SDL_CreateRenderer(window,1,0);
  if( renderer == NULL )
  {
    std::stringstream ss;
    std::cout <<  "Renderer could not be created! SDL_Error: " << SDL_GetError() << std::endl;
    SDL_DestroyWindow(window);
    SDL_Quit();
    return -1;
  }
  SDL_SetWindowPosition(window, SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED);
  
  MandelDraw mdrw(szelesseg,magassag,iteraciosHatar,a,b,c,d);
  
  
  const int FPS = 60;
  const int frame_delay = 1000/FPS;
  Uint32 frame_start;
  int frame_time;
  SDL_Event evt;
  bool should_run = true;
  MouseInput inp={.mbl=false,.mbr=false,.x=0, .y=0};
  while(should_run){
    frame_start = SDL_GetTicks();
    SDL_PollEvent(&evt);
    if(evt.type==SDL_QUIT)should_run = false;
    inp.update(evt);
    mdrw.update(inp);
    mdrw.render(renderer);
    frame_time = SDL_GetTicks() - frame_start;
    if(frame_delay > frame_time){
      SDL_Delay(frame_delay-frame_time);
    }
    
  }
  SDL_DestroyRenderer(renderer);
  SDL_DestroyWindow(window);
  SDL_Quit();
  return 0;
}