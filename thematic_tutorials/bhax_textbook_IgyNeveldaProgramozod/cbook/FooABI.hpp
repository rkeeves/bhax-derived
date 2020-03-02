#ifndef FOOABI_H
#define FOOABI_H

#include <memory>

class FooABI{
public:
	~FooABI();
	
	FooABI(const FooABI&) = delete;
	
	FooABI& operator=(const FooABI&) = delete;
	
	FooABI(FooABI&&) = delete;
	
	FooABI& operator=(FooABI&&) = delete;
public:
  FooABI(int v);
  
  int getA() const;
private:
  struct Impl;
	std::unique_ptr<struct Impl> impl_;
};
#endif