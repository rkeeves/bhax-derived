#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
 
typedef void (*t_handler_fn)(int);

void do_nothing(int signum){
    printf("Ez lesz az elso sor! %i\n",signum);
}

void do_exit(int signum)
{
  printf("Ez lesz a harmadik sor! %i\n",signum);
  exit(0);
}

int main(void)
{
    t_handler_fn last;
    last = signal(SIGTERM, do_nothing);
    raise(SIGTERM);
    printf("Ez lesz a masodik sor!\n");
    last = signal(SIGTERM, do_exit);
    raise(SIGTERM);
    printf("De ide nem jut el\n");
    return 0;
}