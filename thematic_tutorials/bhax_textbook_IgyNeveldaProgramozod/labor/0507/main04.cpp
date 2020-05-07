// Redone whole code from scratch for clarity
#include <iostream>
#include <fstream>
#include <iterator>
#include <set>
#include <iomanip>

#include <valarray>
#include <filesystem>

#include "z3a18qa5_from_scratch.h"

#include <boost/graph/adjacency_list.hpp>
#include <boost/graph/graphviz.hpp>

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

  double mean()
  {
    return 
    static_cast<double>( (*vap).sum() ) 
    / 
    static_cast<double>(  (*vap).size() ) ;
  }
};

// direct copy of future 4
// https://github.com/nbatfai/future/blob/master/cs/future4.cpp

typedef boost::adjacency_list < boost::listS, boost::vecS, boost::directedS,
        boost::property < boost::vertex_name_t, std::string,
        boost::property<boost::vertex_index2_t, int >> ,
        boost::property<boost::edge_weight_t, int >> ZLWGraph;

typedef boost::graph_traits<ZLWGraph>::vertex_descriptor ZLWGraphVertex;
typedef boost::property_map<ZLWGraph, boost::vertex_name_t>::type VertexNameMap;
typedef boost::property_map<ZLWGraph, boost::vertex_index2_t>::type VertexIndexMap;

void print_graph(ZLWGraph &zlw_graph, VertexNameMap &v2str, std::string &zlw)
{

    std::fstream graph_log(zlw + ".dot" , std::ios_base::out);
    boost::write_graphviz(graph_log, zlw_graph, boost::make_label_writer(v2str));

}

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
    std::cout<< entry1.path().string() << " " ;
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
        if(isbi1 != isbi1END && isbi2 != isbi2END)
        {
          zlwBt << *isbi1;
          ++isbi1;
          zlwBt << *isbi2;
          ++isbi2;
        }else{
          isbi1 = isbi1END;
          isbi2 = isbi2END;
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
  
  double mean = m.mean();
  Matrix<int> gm(size,size);
  for(int i(0); i<size; ++i){
    for(int j(0); j<size; ++j){
      //std::cout << std::setw(8) << m[i][j]<<" ";
      if(m[i][j]<mean){
        gm[i][j]=1;
      }else{
        gm[i][j]=0;
      }
      std::cout << std::setw(3) << gm[i][j]<<" ";
    }
    std::cout << std::endl;
  }

  ZLWGraph zlw_graph;
  VertexNameMap v2str = boost::get(boost::vertex_name, zlw_graph);
  VertexIndexMap v2idx = boost::get(boost::vertex_index2, zlw_graph);
  std::map <std::string, ZLWGraphVertex> str2v;
  
  int idx = 0;
  for(const auto & entry1 : std::filesystem::directory_iterator(path))
  {
    
    ZLWGraphVertex v = boost::add_vertex(zlw_graph);
    v2str[v] = entry1.path().string();
    v2idx[v] = idx++;
    str2v[entry1.path().string()] = v;
  }
 
 

  r = c = 0;
  
  for(const auto & entry1 : std::filesystem::directory_iterator(path))
  {
    for(const auto & entry2 : std::filesystem::directory_iterator(path))
    {
      if(m[r][c] < mean){
        boost::add_edge(
          str2v[entry1.path().string()], 
          str2v[entry2.path().string()], 
          1, 
          zlw_graph);
      }
      ++c;
    }
    ++r;
    c=0;
  }

  std::string  dot_fname("testgraph");
  print_graph(zlw_graph, v2str, dot_fname);
  
  return 0;
}