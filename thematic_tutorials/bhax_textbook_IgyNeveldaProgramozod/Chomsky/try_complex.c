#include <complex.h>
#include <tgmath.h>
#include <stdio.h> 
int main(void)
{
  double complex z1 = I * I;
  printf("I * I = %.1f%+.1fi\n", creal(z1), cimag(z1));
  return 0;
}