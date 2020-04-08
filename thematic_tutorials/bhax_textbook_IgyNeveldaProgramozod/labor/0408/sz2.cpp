#include <iostream>

int main()
{
  int nt[] = {1,2,3};
  int* end = nt + sizeof(nt)/sizeof(int);
  
  std::cout 
    << nt << std::endl
    << end << std::endl;
  
  for(int* it=nt;it!=end;++it){
   std::cout << *it << " ";
  }
  return 0;
}