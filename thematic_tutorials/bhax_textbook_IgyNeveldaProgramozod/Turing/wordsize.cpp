#include <stdio.h>
#include <limits.h>

int main()
{
    int a = INT_MAX;
    int i = 0;
    while(a!=0){
        a = a>>1;
        i++;
    }
    printf("Shift count was %d\n",i);
    printf("Size of int is %d\n",((i+1)/8));
    printf("Size of int is %d\n",sizeof(int));
    return 0;
}