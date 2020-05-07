// Redone whole code from scratch for clarity
#include <iostream>
#include <fstream>
#include <iterator>
#include <set>
#include <iomanip>

#include <valarray>
#include <filesystem>

#include "z3a18qa5_from_scratch.h"

template
<typename NumT>
class Slice_iter
{
// [STATE]
private:
  std::valarray<NumT> * vap;
  
  std::slice sl;
// [LC]
public:
  Slice_iter(std::valarray<NumT> * vap, std::slice sl)
  : vap(vap), sl(sl)
  {
    
  }
// [API]
public:
  NumT& operator[](int n){
    return (*vap)[sl.start()+n*sl.stride()];
  }
};

template
<typename NumT>
class Matrix
{
// [STATE]
private:
  std::valarray<NumT> * vap;
  
  int nor;
  
  int noc;
// [LC]
public:

  Matrix(int nor, int noc)
  : nor(nor), noc(noc), vap(new std::valarray<NumT>(nor*noc))
  {
    
  }

  ~Matrix()
  {
    if(vap!=nullptr){delete vap;}
  }
// [API]
public:
  Slice_iter<NumT> operator[](int n){ return row(n);}

  Slice_iter<NumT> row(int n){ return Slice_iter<NumT>(vap,std::slice(n*noc, noc, 1));}
};

int main(int argc, char** argv)
{
  std::string path("genomes_bin");
  
  auto di = std::filesystem::directory_iterator(path);
  const int size = std::distance(std::filesystem::begin(di), std::filesystem::end(di)); 
  
  Matrix<double> m(size,size);
  
  std::cout << size << std::endl;
  int r(0), c(0);
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
      std::cout << std::setw(8) << (m[r][c] = zlwBt.getVar());
      ++c;
    }
    ++r; c=0;
    std::cout << std::endl;
  }
  
  std::cout << std::endl;
  std::cout << std::endl;
  
  
  for(int i(0); i<size; ++i){
    for(int j(0); j<size; ++j){
      std::cout << std::setw(8) << m[i][j]<<" ";
    }
    std::cout << std::endl;
  }
   
  return 0;
}