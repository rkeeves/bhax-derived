#include "FooABI.hpp"

struct FooABI::Impl
{
  Impl(int v) : a(v){};
  int a;
};
  
FooABI::FooABI(int v) : impl_(new Impl(v)){

}

FooABI::~FooABI() = default;

int FooABI::getA() const
{
  return impl_->a;
}