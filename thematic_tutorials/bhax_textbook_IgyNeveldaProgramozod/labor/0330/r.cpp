#include <iostream>
#include <functional>
#include <random>

class Unirand
{
  public:
    Unirand(long seed, int min, int max) 
    : 
    random(
      std::bind(
        std::uniform_int_distribution<int>(min,max) ,
        std::default_random_engine(seed)))
    {
      
    }

    int operator()(){ return random();  }
    
  private:
    std::function<int()> random;
};

int main()
{
  Unirand ur(0,0,2);
  for(int i=0; i<5;++i)
  {
    std::cout<<ur()<<std::endl;
  } 
}