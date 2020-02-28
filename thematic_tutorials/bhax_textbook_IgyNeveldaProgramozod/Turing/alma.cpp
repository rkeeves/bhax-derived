#include <iostream>

#ifdef ALMA
void hello()
{
  std::cout << "Hello world!" << std::endl;
};
#endif

int main ()
{
  hello();
  return 0;
}
