#ifndef INT_H
#define INT_H

#include <iostream>

class Int
{
  
private:
  int *value;
  int bogoc;
  
public:
  static int bogoCount;
  
  Int(int value=0):value(new int(value))
  {
    bogoc = bogoCount++;
    std::cout<<"Int ctor "<<bogoc<<" "<<*this->value<<" "<<this->value<<" "<<this<<std::endl;
  }
  
  Int(const Int& old) : value(new int(*old.value))
  {
    bogoc = bogoCount++;
    std::cout<<"Int copy ctor "<<bogoc<<" "<<*this->value<<" "<<this->value<<" "<<this<<std::endl;
  }
  
  Int& operator=(const Int& old)
  {
    Int tmp(old);
    std::swap(*this,tmp);
    std::cout<<"Int copy assign "<<bogoc<<" "<<*this->value<<" "<<this->value<<" "<<this<<std::endl;
    return *this;
  }
  
  Int(Int&& old)
  {
    value = nullptr;
    *this = std::move(old);
    bogoc = bogoCount++;
    std::cout<<"Int move ctor "<<bogoc<<" "<<*this->value<<" "<<this->value<<" "<<this<<std::endl;
  }
  
  Int& operator=(Int&& old)
  {
    std::swap(value,old.value);
    std::cout<<"Int move assign "<<bogoc<<" "<<*this->value<<" "<<this->value<<" "<<this<<std::endl;
    return *this;
  }
  
  ~Int()
  {
    delete value;
    std::cout<<"Int dtor "<<bogoc<<" "<<" "<<this->value<<" "<<this<<std::endl;
  }
};

#endif