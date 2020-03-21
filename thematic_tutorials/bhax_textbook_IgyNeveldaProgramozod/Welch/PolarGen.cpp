#include <iostream>
#include <stdlib.h> // random
#include <math.h> // sqrt, log and rick n morty

class PolarGen
{
public:

  PolarGen() : store_empty(true){ }
  
  double next()
  {
    if(store_empty) {
      double u1, u2, v1, v2, w;
      do {
          u1 = rnd01();
          u2 = rnd01();
          v1 = 2*u1 - 1;
          v2 = 2*u2 - 1;
          w = v1*v1 + v2*v2;
      } while(w > 1);
      double r = sqrt((-2*log(w))/w);
      stored = r*v2;
      store_empty = false;
      return r*v1;
    }else {
      store_empty = true;
      return stored;
    }
  }
private:

  double rnd01(){return rand() / (RAND_MAX + 1.);}
  
  double rnd(double min_inclusive, double max_exclusive)
  {
    return min_inclusive + (rand() / ( RAND_MAX / (max_exclusive-min_inclusive) ) ) ;  
  }
private:
  bool store_empty;
  double stored;
};

int main()
{
  PolarGen pg;
  for(int i=0; i<10; ++i){ std::cout<< pg.next() << std::endl;}
  return 0;
}