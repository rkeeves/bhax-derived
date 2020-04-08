#include <iostream>

int main()
{
  char cl[] = "alma";
  for(char* it=cl;*it!='\0';++it){
   std::cout << *it;
  }
  return 0;
}