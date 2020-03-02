#include "Foo.hpp"

Foo::Foo(int v){
  this->a = v;
}

int Foo::getA() const
{
  return this->a;
}