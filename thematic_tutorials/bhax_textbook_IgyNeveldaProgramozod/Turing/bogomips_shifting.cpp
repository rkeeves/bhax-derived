#include <iostream>

int main()
{
  unsigned long long a = 1;
  int i = 0;
  while(a!=0)
  {
    a<<=1;
    std::cout<< a << std::endl;
    i++;
  }
  std::cout<< i << std::endl;
  return 0;
}