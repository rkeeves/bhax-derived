#include <stdio.h>
#include <stdlib.h>

int
main ()
{
    int nr = 5;
    double **tm;
    
    printf("%p\n", &tm);
    
    if ((tm = (double **) malloc (nr * sizeof (double *))) == NULL)
    {
        return -1;
    }

    printf("%p\n", tm);
    
    for (int i = 0; i < nr; ++i)
    {
        if ((tm[i] = (double *) malloc ((i + 1) * sizeof (double))) == NULL)
        {
            return -1;
        }

    }

    printf("%p\n", tm[0]);    
    
    for (int i = 0; i < nr; ++i)
        for (int j = 0; j < i + 1; ++j)
            tm[i][j] = i * (i + 1) / 2 + j;

    for (int i = 0; i < nr; ++i)
    {
        for (int j = 0; j < i + 1; ++j)
            printf ("%f, ", tm[i][j]);
        printf ("\n");
    }

    tm[3][0] = 42.0;
    //(*(tm + 3))[1] = 43.0;
    printf("tm addr %p\n", &tm);
    printf("tm[0] addr %p\n", &tm[0]);
    printf("tm[1] addr %p\n", &tm[1]);
    long long unsigned int pa = (long long unsigned int) &tm[1];
    long long unsigned int pb = (long long unsigned int) &tm[0];
    printf("dist tm[1] tm[0] %lu\n", ( pa - pb ));
    printf("tm[4] addr %p\n", &tm[4]);
    printf("(tm + 3)[1] addr %p\n", &((tm + 3)[1]));
    double* p = (tm + 3)[1];
    printf("p, tm[4][0] %p, %p\n", p, &tm[4][0]);
    printf("*(tm + 3)[1], tm[4][0] %lf , %lf \n", *(tm + 3)[1],tm[4][0]);
    *(tm + 3)[1] = 43.0;
    *(tm[3] + 2) = 44.0;
    *(*(tm + 3) + 3) = 45.0;

    for (int i = 0; i < nr; ++i)
    {
        for (int j = 0; j < i + 1; ++j)
            printf ("%f, ", tm[i][j]);
        printf ("\n");
    }

    for (int i = 0; i < nr; ++i)
        free (tm[i]);

    free (tm);

    return 0;
}
