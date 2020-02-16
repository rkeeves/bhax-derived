#include "pubghypetrain.h"
#include <iostream>
#include <chrono>
#include <thread>

using namespace std;

void collide(int posX, int posY, int &velX, int &velY, int xmax, int ymax){
  bool outX = (posX <= 0 || posX >= xmax-1);
  velX += -2 * velX * outX;
  bool outY = (posY <= 0 || posY >= ymax-1);
  velY += -2 * velY * outY;
};

void moveBall(int &posX, int &posY, int velX, int velY){
  posX += velX;
  posY += velY;
};

void draw(int posX, int posY, int xmax, int ymax){
  ClearScreen();
  for(int y = 0; y < ymax; y++){
    for(int x = 0; x < xmax; x++){
      if( (posX == x) && (posY == y) ){cout << 'O';}else{cout << '.';};
    };
    cout << endl;
  };
};

int main(){
  int T_SLEEP_MILLIS = 50; 
  int GLOBAL_H = 10;
  int GLOBAL_W = 10;
  int posX = 5;
  int posY = 2;
  int velX = 2;
  int velY = -1;
  for(int i = 0; i < 50; i++){
    moveBall(posX,posY,velX,velY);
    collide(posX,posY,velX,velY,GLOBAL_H,GLOBAL_W);
    draw(posX,posY,GLOBAL_H,GLOBAL_W);
    sleep(T_SLEEP_MILLIS);
  };
  return 0;
};