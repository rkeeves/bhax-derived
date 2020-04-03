#include <stdio.h>

void cs(int a, int b)
{
  a=a-b;
  b=b+a;
  a=b-a;
  
  printf("cs %d %d\n", a , b);
}

void cs2(int* a, int* b)
{
  *a=*a-*b;
  *b=*b+*a;
  *a=*b-*a;
  
  printf("cs %d %d\n", *a , *b);
}

int main()
{
  int a = 5;
  int b = 7;
  printf("main %d %d\n", a , b);
  cs(a,b);
  printf("main %d %d\n", a , b);
  cs2(&a,&b);
  printf("main %d %d\n", a , b);
  return 0;
}