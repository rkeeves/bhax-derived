#include <stdio.h>

int main()
{
  int *p;
  int (*ptr)[5];
  
  int array[5]={1,2,3,4,5};
  p = array;
  ptr = &array;
  
  printf("%p %p \n", p, ptr);
  
  printf("%p %p \n", ++p, ++ptr);
  // minimum
  // - c
  // - c++, new operator
  // még plusz
  // - két dimenziós tömböt
  // - referenciál. öt egészből álló tömb referencia
  //   (referencia, melyet lehet indexálni)
  // - hogy lehet átadni két dim tömböt fgv-nek
  return 0;
}