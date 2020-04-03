#include <iostream>

int fgv(){return 42;}

int main()
{
  int n=7;
  int &lvr(n);
  int &&rvr(8);
  
  std::cout<<lvr<<" "<<rvr<<std::endl;
  lvr = rvr;
  
  std::cout<<lvr<<" "<<rvr<<std::endl;
  int &&rv2r(fgv());
  std::cout<<lvr<<" "<<rvr<<" "<<rv2r<<std::endl;
  //int &lvr2(3);
  return 0;
}