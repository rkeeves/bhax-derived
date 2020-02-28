#include <iostream>
#include <vector>
#include <cmath>
#include <fstream>
#include <iomanip> 


void kiir(std::ofstream& os, const std::vector<double> &v){
  int sz = v.size();
  if(sz<1) return;
  os << v[0];
  for(int i=1; i<sz; i++)
     os << ";" << v[i];
  os << std::endl;
}

double tavolsag(const std::vector<double> &PR, const std::vector<double> &PRv){
  int i;
  double osszeg = 0;
  for(i=0;i<PR.size();++i) osszeg += (PRv[i]-PR[i])*(PRv[i]-PR[i]);
  return sqrt(osszeg);
}

std::vector<std::vector<double>> get_batfai_graph()
{
  return std::move(std::vector<std::vector<double>>{
    /*J*/   { 0, 0, 1, 0},
    /*JP*/  { 1, 1, 1, 1},
    /*JPL*/ { 0, 1, 0, 0},
    /*M*/   { 0, 0, 1, 0}
  });
}

std::vector<std::vector<double>> get_dangling()
{
  return std::move(std::vector<std::vector<double>>{
    { 1,0,1,0,1,0},
    { 1,1,0,1,0,0},
    { 0,1,1,0,1,0},
    { 1,0,1,1,0,0},
    { 0,1,0,1,1,0},
    { 1,0,1,0,1,1}
  });
}

std::vector<std::vector<double>> get_symm()
{
  return std::move(std::vector<std::vector<double>>{
    { 0,1 },
    { 1,0 }
  });
}

void normalize(std::vector<std::vector<double>>& quadmx)
{
  int size = quadmx.size();
  for(int j=0; j<size; j++){
    double sum = 0;
    for(int i=0; i<size; i++){
      sum+=quadmx[i][j];
    }
    if(sum!=0){
      for(int i=0; i<size; i++){
      quadmx[i][j]/=sum;
      }
    }
  }
}

int main(void){
  // ha probalgatod es ne adj isten valami miatt ne konvergalna
  // akkor ennyi iter utan auto kilep a fo loopbol
  int SAFE_LIMIT = 100;
  std::ofstream outf;
  outf.open ("pagerank.csv");
  // at this point L is not normalized
  std::vector<std::vector<double>>L{std::move(get_dangling())};
  normalize(L);
  int size = L.size();
  std::vector<double> PR;
  for(int i = 0; i<size; i++)PR.push_back(0.0);
  std::vector<double> PRv;
  for(int i = 0; i<size; i++)PRv.push_back(1.0/size);
  for(int c;c<SAFE_LIMIT;c++){
    for(int i = 0; i < size; ++i){
      PR[i] = 0.0;
      for(int j = 0; j < size; ++j) PR[i] += L[i][j] * PRv[j];
    }
    kiir(outf,PR);
    if(tavolsag(PR,PRv)<0.0000000001) break;
    PRv = PR;
  }
  outf.close();
  return 0;
}