// Uses SDL2, because of fun and the fact that png++ uses non standard strerror_r
// For compilation use
// -Wl,-subsystem,windows -lmingw32 -lSDL2main -lSDL2
// KAWOOOSH

#include <iostream>
#include <complex>
#define SDL_MAIN_HANDLED 
#include <sdl2/SDL.h>

class BiomorphDraw
{
public:
  BiomorphDraw(int w,
  int h, 
  int iter_lim,
  double xmin,
  double xmax,
  double ymin,
  double ymax,
  double reC,
  double imC,
  double R)
  
  : w(w),h(h),iter_lim(iter_lim),xmin(xmin),xmax(xmax),ymin(ymin),ymax(ymax),reC(reC),imC(imC),R(R)
  { }
  
  void render(SDL_Renderer *renderer)
  {
    std::complex<double> cc ( reC, imC );
    int szazalek = 0; // unused
    double dx = ( xmax - xmin ) / w;
    double dy = ( ymax - ymin ) / h;
    for ( int y = 0; y < h; ++y ){
        for ( int x = 0; x < w; ++x ){
            double reZ = xmin + x * dx;
            double imZ = ymax - y * dy;
            std::complex<double> z_n ( reZ, imZ );
            int iteracio = 0;
            for (int i=0; i < iter_lim; ++i){
                z_n = std::pow(z_n, 2) + cc;
                if( std::abs(z_n) > R){
                    iteracio = i;
                    break;
                }
            }
            SDL_SetRenderDrawColor(renderer, (iteracio * 20)%255, (iteracio* 40)%255, (iteracio* 60)%255 , 0xff);
            SDL_RenderDrawPoint(renderer, x,y);
        }
    }
  }
private:
  int w;
  int h;
  int iter_lim;
  double xmin;
  double xmax;
  double ymin;
  double ymax;
  double reC;
  double imC;
  double R;
};

int main(int argc, char** argv)
{
  SDL_SetMainReady(); // just for check 
   int szelesseg = 400;
  int magassag = 300;
  int iteraciosHatar = 255;
  double xmin = -1.4;
  double xmax = 1.2;
  double ymin = -1.3;
  double ymax = 1.3;
  //double reC = .285, imC = 0;
  double reC = -0.123, imC = 0.745;
  double R = 10.0;
  if ( argc == 1 ){
    // Use defaults
  }else if ( argc == 11 ){
      szelesseg = atoi ( argv[1] );
      magassag =  atoi ( argv[2] );
      iteraciosHatar =  atoi ( argv[3] );
      xmin = atof ( argv[4] );
      xmax = atof ( argv[5] );
      ymin = atof ( argv[6] );
      ymax = atof ( argv[7] );
      reC = atof ( argv[8] );
      imC = atof ( argv[9] );
      R = atof ( argv[10] );
  }else{
    std::cout << "Hasznalat: ./3.1.2 fajlnev szelesseg magassag iteraciosHatar xmin xmax ymin ymax reC imC R" << std::endl;
    return -1;
  }
  if(SDL_Init(SDL_INIT_VIDEO) != 0){
    std::cout << "SDL_Init failed" << std::endl;
    return -1;
  }
  SDL_Window *window = SDL_CreateWindow( "Biomorph", SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, szelesseg, magassag, SDL_WINDOW_SHOWN );
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
      
  BiomorphDraw bdrw(szelesseg,magassag,iteraciosHatar,xmin,xmax,ymin,ymax,reC,imC,R);
  bdrw.render(renderer);
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

