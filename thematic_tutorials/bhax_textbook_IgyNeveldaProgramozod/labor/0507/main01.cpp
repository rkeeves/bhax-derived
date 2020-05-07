// Redone whole code from scratch for clarity
#include <iostream>
#include <fstream>
#include <iterator>
#include <set>
#include <iomanip>

#include <filesystem>

#include "z3a18qa5_from_scratch.h"

class Covid19Var
{
private:
  std::string loc;
  
  ZLWTree<char,'/','0'> zlwBt;
  
public:
  Covid19Var(std::string fname, std::string loc)
  : loc(loc)
  {
    std::ifstream fis(fname);
    std::istreambuf_iterator<char> isbiEND;
    for(std::istreambuf_iterator<char> isbi(fis); isbi != isbiEND; ++isbi)
      zlwBt << *isbi;
    zlwBt.eval();
  }
  
  
  void print(){zlwBt.print();}
  
  bool operator<(const Covid19Var& o) const{ return zlwBt < o.zlwBt;}
  
  double getMean() const{return zlwBt.getMean();}
  
  double getVar() const{return zlwBt.getVar();}
  
  std::string getLoc() const { return loc; }
};

int main(int argc, char** argv)
{
  std::string path("genomes_bin");
  for(const auto & entry1 : std::filesystem::directory_iterator(path))
  {
    for(const auto & entry2 : std::filesystem::directory_iterator(path))
    {
      ZLWTree<char,'/','0'> zlwBt;
      
      std::ifstream ifs1(entry1.path().string());
      std::istreambuf_iterator<char> isbi1END;
      std::ifstream ifs2(entry2.path().string());
      std::istreambuf_iterator<char> isbi2END;
      for(std::istreambuf_iterator<char> isbi1(ifs1),isbi2(ifs2);
          isbi1 != isbi1END || isbi2 != isbi2END;)
      {
        if(isbi1 != isbi1END)
        {
          zlwBt << *isbi1;
          ++isbi1;
        }
        if(isbi2 != isbi2END)
        {
          zlwBt << *isbi2;
          ++isbi2;
        }
      }
      zlwBt.eval();
      std::cout << std::setw(8) << zlwBt.getVar();
    }
    std::cout << std::endl;
  }
  
  return 0;
}

/*
int main(int argc, char** argv)
{
  Covid19Var cv0("./genomes/LR757998_1.out","LR757998_1 2019-12-26 China: Hubei, Wuhan");
  Covid19Var cv1("./genomes/MT019529_1.out","MT019529_1 2019-12-23 China: Hubei, Wuhan");
  Covid19Var cv2("./genomes/MT412243_1.out","MT412243_1 2020-01-01 USA");
  Covid19Var cv3("./genomes/MT186683_1.out","MT186683_1 2020-01-01 Hong Kong");
  std::set<Covid19Var> cs;
  
  cs.insert(cv0);
  cs.insert(cv1);
  cs.insert(cv2);
  cs.insert(cv3);
  
  for(std::set<Covid19Var>::iterator it = cs.begin(); it!=cs.end(); ++it){
    std::cout << (*it).getLoc() << std::endl;
    std::cout << "mean: " <<(*it).getMean() << ", var: " << (*it).getVar() << std::endl;
    std::cout << std::endl;
  }
  
  return 0;
}*/
