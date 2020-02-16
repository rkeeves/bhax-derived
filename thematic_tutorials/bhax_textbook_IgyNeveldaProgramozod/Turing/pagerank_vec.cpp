#include <iostream>
#include <vector>
#include <cmath>
using namespace std;
void kiir(const vector<double> &v){
  for(int i = 0; i < v.size(); ++i) cout << v[i] << " ";
}

double tavolsag(const vector<double> &PR, const vector<double> &PRv){
  int i;
  double osszeg = 0;
  for(i=0;i<PR.size();++i) osszeg += (PRv[i]-PR[i])*(PRv[i]-PR[i]);
  return sqrt(osszeg);
}

int main(void){
  
  vector<vector<double>>L{
    {0.0,0.0,1.0/3.0,0.0},
    {1.0,1.0/2.0,1.0/3.0,1.0},
    {0.0,1.0/2.0,0.0,0.0},
    {0.0,0.0,1.0/3.0,0.0},
  };
  vector<double> PR{0.0,0.0,0.0,0.0};
  vector<double> PRv{1.0/4.0,1.0/4.0,1.0/4.0,1.0/4.0};
  for(;;){
    for(int i = 0; i < 4; ++i){
      PR[i] = 0.0;
      for(int j = 0; j < 4; ++j) PR[i] += L[i][j] * PRv[j];
    }
    if(tavolsag(PR,PRv)<0.0000000001) break;
    PRv = PR;
  }
  kiir(PR);
  return 0;
}