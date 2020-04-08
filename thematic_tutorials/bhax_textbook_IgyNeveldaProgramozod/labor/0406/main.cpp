#include "z3a18qa5modded.hpp"
#include <string>
#include <iostream>
#include <fstream>
#include <iterator>
#include <set>

class COVID19Var{
  

  
public:
  COVID19Var(std::string loc, std::string fname)
  : loc(loc)
  {
    std::ifstream ifs(fname);
    std::istreambuf_iterator<char> isbiEnd;
    for(std::istreambuf_iterator<char> isbi(ifs);isbi!=isbiEnd; ++isbi){
      zlwBt << *isbi;
    }
    zlwBt.eval();
  }
  
  double getVar() const
  {
    return zlwBt.getVar();
  }
  
  double getMean() const
  {
    return zlwBt.getMean();
  }
  
  void print(){zlwBt.print();}
  
  bool operator<(const COVID19Var& o) const
  {
    return zlwBt < o.zlwBt;
  }
  
  const std::string& getLoc() const{
    return loc;
  }
  
private:
  
  std::string loc;
  ZLWTree<char,'/','0'> zlwBt;
};

int main()
{
  COVID19Var cv0("MN908947_19_12_01","MN908947_19_12_01_bin.txt");
  COVID19Var cv1("LC528232_20_02_10","LC528232_20_02_10_bin.txt");
  COVID19Var cv2("MN975262_20_01_11","MN975262_20_01_11_bin.txt");
  
  std::set<COVID19Var> cs;
  cs.insert(cv0);
  cs.insert(cv1);
  cs.insert(cv2);
  for(std::set<COVID19Var>::iterator it = cs.begin(); it!=cs.end(); ++it)
  {
      std::cout
        <<(*it).getLoc() << " " << (*it).getMean()<<" "<<(*it).getVar()<<std::endl;
  }
  return 0;
}