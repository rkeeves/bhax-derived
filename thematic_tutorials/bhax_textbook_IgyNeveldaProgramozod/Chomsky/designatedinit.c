#include <stdio.h>

struct my_t{
  int x;
  int y;
};

int main()
{
  struct my_t foo = {.x=1, .y=2};
  printf("%i",foo.x);
  return 0;
}