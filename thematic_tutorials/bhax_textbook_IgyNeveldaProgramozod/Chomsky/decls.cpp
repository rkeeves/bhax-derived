#include <stdio.h>
int val = 8;
int val2 = 16;
// egeszre mutato mutatot visszaado fuggveny
int* get_val_ptr();

int* get_int_ptr(int,int);

int* (*get_fn_ptr(int))(int, int);

int add(int a, int b);

int (*get_addfn_ptr(int a))(int, int);

int main()
{
  // egesz
  int a = 1;
  printf("%i\n",a);
  // egeszre mutato mutato
  int *ptr_a = &a;
  printf("%i\n",*ptr_a);
  // egesz referenciaja
  int& ref_a = a;
  printf("%i\n",ref_a);
  // egeszek tombje
  int arr1[2] = {2,4};
  printf("%i %i\n",arr1[0],arr1[1]);
  // egeszek tombjenek referenciaja
  int (&ref_arr1)[2] = arr1;
  printf("%i\n",ref_arr1[0]);
  // egeszre mutato mutatok tombje
  int* arr2[2];
  arr2[0] = &a;
  arr2[1] = &arr1[1];
  printf("%i %i\n",*arr2[0],*arr2[1]);
  // egeszre mutato mutatot visszaado fuggvenyt mutato mutato
  int* (*fun0) (void) = get_val_ptr;
  printf("%i\n",*fun0());
  // egeszet visszaado es ket egeszet kapo fuggvenyre mutato mutatot visszaado, egeszet kapo fuggveny
  printf("%i\n",*(get_fn_ptr(1)(2,4)));
  // fuggvenymutato egy egeszet visszaado es ket egeszet kapo fuggvenyre mutato mutatot visszaado, egeszet kapo fuggvenyre
  printf("%i\n",get_addfn_ptr(1)(16,16));
  return 0;
}

int* get_val_ptr(){return &val;}

int* get_val2_ptr(int a, int b){return &val2;}

int* (*get_fn_ptr(int a))(int, int){ return get_val2_ptr;};

int (*get_addfn_ptr(int a))(int, int){ return add;};

int add(int a, int b){return a+b;}