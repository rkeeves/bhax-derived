#include "Foo.hpp"
#include <iostream>
int main(){
  Foo foo(1);
  std::cout << foo.getA() << std::endl;
  return 0;
}