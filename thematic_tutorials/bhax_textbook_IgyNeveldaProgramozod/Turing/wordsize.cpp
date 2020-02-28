#include <iostream>

void shift_l()
{
  unsigned int a = 1;
  unsigned int i = 0;
  while(a!=0){
      a = a<<1;
      i++;
  }
  std::cout<<"Shift_L count was " << i << std::endl;
  std::cout<<"Size " << ((i)/8) << std::endl;
  std::cout<<"Sanity Check " << sizeof(int) << std::endl;
}

void shift_r()
{
  unsigned int a = 0;
  a = ~a;
  int i = 0;
  while(a!=0){
      a = a>>1;
      i++;
  }
  std::cout<<"Shift_R count was " << i << std::endl;
  std::cout<<"Size " << ((i)/8) << std::endl;
  std::cout<<"Sanity Check " << sizeof(int) << std::endl;
}

int main()
{
    shift_l();
    shift_r();
    return 0;
}