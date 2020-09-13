#include "polargen.h"

#include <cstdlib>
#include <cmath>
#include <ctime>

namespace prog2{

    PolarGen::PolarGen() : store_empty(true), stored(0.0)
    {
      std::srand( std::time(NULL) );
    }

    
    double PolarGen::next()
    {
      if(store_empty){
        double u1,u2,v1,v2,w;
        do{
          u1 = std::rand() / (RAND_MAX + 1.0);
          u2 = std::rand() / (RAND_MAX + 1.0);
          v1 = 2 * u1 - 1;
          v2 = 2 * u2 - 1;
          w = v1 * v1 + v2 * v2;
        }while(w > 1);
        double r = std::sqrt((-2 * std::log(w)) / w);
        stored = r * v2;
        store_empty = !store_empty;
        return r * v1;
      }else{
        store_empty = !store_empty;
        return stored;
      }
    }
} /* prog2 */

