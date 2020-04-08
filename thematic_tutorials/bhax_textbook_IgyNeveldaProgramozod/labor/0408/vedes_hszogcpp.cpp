#include <iostream>


int main()
{
  const unsigned int nr = 5;
  double **tm;
  tm = new double*[5];
  
  for(int i = 0; i<5;++i)
  {
    tm[i] = new double[i+1];
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
    delete[] tm[i];

  }
  delete[] tm;
  
  return 0;
}