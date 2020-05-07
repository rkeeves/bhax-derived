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
    std::cout<<"ENTER Int ctor "<<bogoc<<" "<<*this->value<<" "<<this->value<<" "<<this<<std::endl;
    bogoc = bogoCount++;
    std::cout<<"EXIT Int ctor "<<bogoc<<" "<<*this->value<<" "<<this->value<<" "<<this<<std::endl;
  }
  
  Int(const Int& old) : value(new int(*old.value))
  {
    std::cout<<"ENTER Int copy ctor "<<bogoc<<" "<<*this->value<<" "<<this->value<<" "<<this<<std::endl;
    bogoc = bogoCount++;
    std::cout<<"EXIT Int copy ctor "<<bogoc<<" "<<*this->value<<" "<<this->value<<" "<<this<<std::endl;
  }
  
  Int& operator=(const Int& old)
  {
    std::cout<<"ENTER Int copy assign "<<bogoc<<" "<<((this->value==nullptr) ? -1 : *this->value)<<" "<<this->value<<" "<<this<<std::endl;
    Int tmp(old);
    std::swap(*this,tmp);
    std::cout<<"EXIT Int copy assign "<<bogoc<<" "<<*this->value<<" "<<this->value<<" "<<this<<std::endl;
    return *this;
  }
  
  Int(Int&& old)
  {
    std::cout<<"ENTER Int move ctor "<<bogoc<<" "<<*this->value<<" "<<this->value<<" "<<this<<std::endl;
    value = nullptr;
    *this = std::move(old);
    bogoc = bogoCount++;
    std::cout<<"EXIT Int move ctor "<<bogoc<<" "<<*this->value<<" "<<this->value<<" "<<this<<std::endl;
  }
  
  Int& operator=(Int&& old)
  {
    std::cout<<"ENTER Int move assign "<<bogoc<<" "<<((this->value==nullptr) ? -1 : *this->value)<<" "<<this->value<<" "<<this<<std::endl;
    std::swap(value,old.value);
    std::cout<<"EXIT Int move assign "<<bogoc<<" "<<*this->value<<" "<<this->value<<" "<<this<<std::endl;
    return *this;
  }
  
  ~Int()
  {
    delete value;
    std::cout<<"Int dtor "<<bogoc<<" "<<" "<<this->value<<" "<<this<<std::endl;
  }
};

#endif