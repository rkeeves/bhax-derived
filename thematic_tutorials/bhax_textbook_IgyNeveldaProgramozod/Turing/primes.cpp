#include <iostream>
#include <bitset>
#include <math.h>   // sqrt

const int N = 100; // The catch is we have to know N compile time

int main()
{
  auto bs = std::move(std::bitset<N+1>{}.set());
  bs.set(0,false);
  bs.set(1,false);
  int m = sqrt(N); 
    for (int p=2; p<=m; p++)
        if (bs.test(p))
            for (int i=p*2; i<=N; i += p)
              bs.set(i,false);
  double b2approx = 0.0;
  for(int i = 2; i <bs.size();i++)
    if(bs.test(i) && bs.test(i-2))
      b2approx+=1.0/(i-2)+1.0/i;
  std::cout<<"B2 approximation for "<<N<<" is "<< b2approx <<std::endl;
  return 0;
}
/* N        B2approx
 * 10       0.87619
 * 100      1.33099
 * 1000     1.51803
 * 10000    1.61689
 * 100000   1.6728
 * 1000000  1.71078
 * 10000000 1.73836
 */
/*
 * Didn't use accu, functional or lambdas
 * But you could do it based on nbatfais R code...
 * std::accumulate(t1.begin(), t1.end(), 0.0, [](const double &a, const double &b){return a+(1.0/b)+(1/(b+2.0));});
 */