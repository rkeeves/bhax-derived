#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
using namespace std;

typedef vector<double>::iterator dbl1dit;
typedef vector<vector<double>>::iterator dbl2dit;

void kiir(const vector<double> &v){
  for(auto it = v.begin(); it != v.end(); it++) cout << *it << " ";
}

double tavolsag(vector<double> &PR, vector<double> &PRv){
  double s = 0;
  for(pair<dbl1dit,dbl1dit> itr(PR.begin(), PRv.begin()); itr.first != PR.end(); ++itr.first, ++itr.second)
     s += (*itr.second-*itr.first)*(*itr.second-*itr.first);
  return sqrt(s);
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
    for (pair<dbl1dit, dbl2dit> l1(PR.begin(),L.begin()); l1.first != PR.end(); ++l1.first, ++l1.second)
    {
        *l1.first = 0;
        for (pair<dbl1dit, dbl1dit> l2(PRv.begin(),l1.second->begin()); l2.first != PRv.end(); ++l2.first, ++l2.second)
          *l1.first += (*l2.first) * (*l2.second);
    }
    if(tavolsag(PR,PRv)<0.0000000001) 
      break;
    PRv = PR;
  }
  kiir(PR);
  return 0;
}