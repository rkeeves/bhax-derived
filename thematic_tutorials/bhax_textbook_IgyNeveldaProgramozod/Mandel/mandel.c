#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int mandel(
    float c_re, 
    float c_im, 
    int max_iter)
{
  int i = 0;
  float temp=0.0, z_re=0.0, z_im=0.0;
  while ( ( ((z_re*z_re)+(z_im*z_im)) < 4.0 ) && ( i < max_iter ) )
  {
    temp =(z_re*z_re)-(z_im*z_im)+c_re;
    z_im = 2.0*(z_re*z_im)+c_im;
    z_re=temp;
    i++;
  }
  return i;
}

void apply_mandel(
    int** quad_mx, 
    int re_size, 
    int im_size,
    float re_lo, 
    float re_hi, 
    float im_lo, 
    float im_hi, 
    int max_iter)
{
  
  int re_step,im_step,itercount;
  float d_re,d_im,c_re,c_im;
  if(quad_mx == NULL || re_size < 1 || im_size < 1) return;
  d_re = (re_hi-re_lo)/re_size;
  d_im = (im_hi-im_lo)/im_size;
  for(re_step = 0; re_step<re_size;++re_step){
    c_re = re_lo + d_re * re_step;
    for(im_step = 0; im_step<im_size;++im_step){
      c_im = im_hi - d_im * im_step;
      itercount = mandel( c_re,c_im,max_iter);
      quad_mx[re_step][im_step]=mandel( c_re,c_im,max_iter);
    }
  }
}

#define MANDEL_RE_SIZE 20
#define MANDEL_IM_SIZE 16
#define MANDEL_ITER_MAX 255

#define MANDEL_RE_LO -2.0
#define MANDEL_RE_HI  0.7
#define MANDEL_IM_LO -1.35
#define MANDEL_IM_HI  1.35

int main()
{
  float re_lo, re_hi,im_lo,im_hi;
  scanf("%f %f %f %f", &re_lo, &re_hi, &im_lo, &im_hi);
  if(re_lo >= re_hi || im_lo >= im_hi){
   printf("bad limits");
    return 1;
  }
  int n_re,n_im,x,y;
  int** mx;
  n_re = MANDEL_RE_SIZE;
  n_im = MANDEL_IM_SIZE;
  if ((mx = (int **) malloc (n_re * sizeof (int *))) == NULL){ return -1;}
  for (x = 0; x < n_re; ++x){
    if ((mx[x] = (int *) malloc (n_im * sizeof (int))) == NULL){return -1;}
  }
  apply_mandel(mx,n_re,n_im,re_lo,re_hi,im_lo,im_hi,MANDEL_ITER_MAX);
  for(y = 0; y < n_im; ++y){
      for(x = 0; x < n_re; ++x){
          printf("%3d ",mx[x][y]);
      }
      printf("\n");
  }
  for (x = 0; x < n_re; ++x){
    free (mx[x]);
  }
  free (mx);
  return 0; 
}