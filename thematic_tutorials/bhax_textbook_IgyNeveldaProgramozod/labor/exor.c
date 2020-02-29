#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include <io.h>
#include <unistd.h>
#include <string.h>

#define MAX_KULCS 100
#define BUFFER_MERET 256

void usage(){
  printf("exor [KEY] [INF] [OUTF]");
}

int
main (int argc, char **argv)
{
  int status = 0;
  char kulcs[MAX_KULCS];
  char buffer[BUFFER_MERET];
  int kulcs_index = 0;
  int olvasott_bajtok = 0;
  FILE *fdi = 0;
  FILE *fdo = 0;
  int kulcs_meret = 0;
  
  if (argc != 4){
    printf("Bad args \n");
    usage();
    return 1;
  }
  
  kulcs_meret = strlen (argv[1]);
  if (kulcs_meret < 1){
    printf("Key was empty \n");
    usage();
    return 1;
  }
  strncpy (kulcs, argv[1], MAX_KULCS);
  
  fdi = fopen(argv[2], "rb");
  if (fdi == 0){
    printf("Failed to open in file \n");
    usage();
    return 1;
  }
  
  fdo = fopen(argv[3], "wb");
  if (fdo == 0){
    printf("Failed to open out file \n");
    usage();
    return 1;
  }
  
  while ((olvasott_bajtok = fread (buffer, 1, BUFFER_MERET, fdi)))
  {
    for (int i = 0; i < olvasott_bajtok; ++i)
    {
      buffer[i] = buffer[i] ^ kulcs[kulcs_index];
      kulcs_index = (kulcs_index + 1) % kulcs_meret;
    }
    fwrite (buffer,1, olvasott_bajtok,fdo);
  }
}
