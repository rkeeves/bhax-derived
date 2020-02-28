#include <iostream>

int main()
{
  unsigned char a = 255;
  for(int i = 0; i<8; i++)
  {
    a=a>>1;
    std::cout<< ((int)a) << std::endl;
  }
  return 0;
}