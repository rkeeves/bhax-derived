#include <stdio.h>
#include <stdlib.h>


int main()
{
  const unsigned int nr = 5;
  double **tm;
  tm = (double**) malloc(nr*sizeof(double*));
  
  for(int i = 0; i<5;++i)
  {
    tm[i] = (double*)malloc((i+1)*sizeof(double));
  }
  
  for(int i = 0; i<5;++i){
    for(int j = 0; j<i+1;++j){
      tm[i][j]=i;
    }
  }
  *(*(tm+1)+1) = 5;
  
  
  for(int i = 0; i<5;++i)
  {
    for(int j = 0; j<i+1;++j)
    {
      printf("%f ",tm[i][j]);
    }
    printf("\n");
  }
  
  
  for(int i = 0; i<5;++i)
  {
    free(tm[i]);

  }
  free(tm);
  
  return 0;
}