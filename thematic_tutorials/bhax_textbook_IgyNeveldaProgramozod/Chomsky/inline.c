#include <stdio.h>

extern inline int getval(void);

int main()
{
  int a = getval();
  printf("%i",a);
  return 0;
}

inline int getval(void){return 1;}