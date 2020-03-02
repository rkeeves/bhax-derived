#include "FooABI.hpp"
#include <iostream>
int main(){
  FooABI fooabi(1);
  std::cout << fooabi.getA() << std::endl;
  return 0;
}