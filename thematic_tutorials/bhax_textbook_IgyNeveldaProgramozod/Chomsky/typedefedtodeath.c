#include <stdio.h>

typedef int ErrCode;

typedef void* FooUserData;

typedef ErrCode (*FooVisitFunction) (int, FooUserData);

struct CustomData{int a;};

int arr[] = {0,1,2,3,4,5};

int visit(int data, FooUserData ud)
{
  CustomData* custom = (CustomData*) ud;
  if(data>custom->a){return 1;}
  printf("Visiting %i\n",data);
  return 0;
}

int do_visit(FooVisitFunction fn, FooUserData ud)
{
  int status = 0;
  for(int i = 0; i<6;i++){
    status = fn(arr[i],ud);
    if(status !=0){ break; }
  }
  return status;
}

int main(){
  CustomData cd = {.a=4};
  do_visit(visit,(void*)&cd);
  return 0;
}