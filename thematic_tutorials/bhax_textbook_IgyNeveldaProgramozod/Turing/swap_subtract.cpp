#include <stdio.h>

int main(){
    int a = 6;
    int b = 7;
    printf("Let a = %d, b = %d\n",a,b);
    a = a + b;
    printf("(1) a = a + b = %d\n",a);
    b = a - b;
    printf("(2) b = a - b = %d\n",b);
    a = a - b;
    printf("(3) a = a - b = %d\n",a);
    printf("Result after swap by addition and subtract is a = %d, b = %d\n",a,b);
    return 0;
}