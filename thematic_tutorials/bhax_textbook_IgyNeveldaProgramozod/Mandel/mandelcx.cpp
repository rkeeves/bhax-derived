// Uses SDL2, because of fun and the fact that png++ uses non standard strerror_r
// For compilation use
// -Wl,-subsystem,windows -lmingw32 -lSDL2main -lSDL2
// KAWOOOSH

#include <iostream>
#include <complex>
#define SDL_MAIN_HANDLED 
#include <sdl2/SDL.h>

class MandelDraw
{
public:
  MandelDraw(int w,int h, int iter_lim,double a,double b,double c,double d)
  : w(w),h(h),iter_lim(iter_lim),a(a),b(b),c(c),d(d)
  { }
  
  void render(SDL_Renderer *renderer)
  {
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
      szazalek = ( double ) j / ( double ) h * 100.0;
    }
  }
private:
  int w;
  int h;
  int iter_lim;
  double a;
  double b;
  double c;
  double d;
};

int main(int argc, char** argv)
{
  SDL_SetMainReady(); // just for check 
  int szelesseg = 400;
  int magassag = 300;
  int iteraciosHatar = 255;
  double a = -1.9;
  double b = 0.7;
  double c = -1.3;
  double d = 1.3;
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
  mdrw.render(renderer);
  SDL_RenderPresent(renderer);
  
  const int FPS = 60;
  const int frame_delay = 1000/FPS;
  Uint32 frame_start;
  int frame_time;
  SDL_Event evt;
  bool should_run = true;
  while(should_run){
    frame_start = SDL_GetTicks();
    SDL_PollEvent(&evt);
    if(evt.type==SDL_QUIT)should_run = false;
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