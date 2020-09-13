#ifndef POLARGEN_H
#define POLARGEN_H

namespace prog2{
  class PolarGen
  {
  
  public:
    PolarGen();
    
    ~PolarGen() = default;
    
    double next();
    
  private:
    
    bool store_empty;
    
    double stored;
    
  };
} /* prog2 */
#endif /* POLARGEN_H */
