#include <iostream>
#include "polargen.h"

int main(int argc, char** argv){
  prog2::PolarGen g;
  for(int i = 0; i < 10; ++i)
    std::cout<<g.next()<<std::endl;
}
