#include <stdio.h>

void swap1(int *pa,int* pb ){
    int t = *pa;
    *pa = *pb;
    *pb = t;
}

void swap2(int *pa,int* pb ){
    *pa = *pa + *pb;
    *pb = *pa - *pb;
    *pa = *pa-*pb;
}

int main()
{
    int a = 6;
    int b = 7;
    printf("Let a = %d, b = %d\n",a,b);
    swap1(&a,&b);
    printf("Swap1 %d,%d\n",a,b);
    swap2(&a,&b);
    printf("Swap2 %d,%d\n",a,b);
    return 0;
}