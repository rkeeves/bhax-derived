/*
 BHAX 391 adás - Lab4.: Matrix design init hack, COVID19 - Corona Em. Brdc. - Stay Home
 https://www.twitch.tv/videos/588138046
 https://youtu.be/oLErhmTA2Z0

 BHAX 407 adás - Lab5.: Matrix design/2 init hack, COVID19 - Corona Em. Brdc. - Stay Home
 lásd még Stroustrup könyv, Kiskapu, 2001, II. kötet, 906. o.
 https://www.twitch.tv/videos/593713517
 https://youtu.be/dxsYdX0RoDM
 */
// g++8 main.cpp -o main -std=c++17 -lstdc++fs -lboost_system
#include "z3a18qa5_from_scratch.h"

#include <fstream>
#include <iomanip>
#include <filesystem>
#include <valarray>

#include <boost/graph/adjacency_list.hpp>
#include <boost/graph/graphviz.hpp>

template <typename NumT>
class Slice_iter {
private:
     std::valarray<NumT> *vap;
     std::slice sl;
public:
     Slice_iter ( std::valarray<NumT> *vap, std::slice sl ) :vap ( vap ), sl ( sl ) {}


     NumT & operator[] ( int n )
     {
          return ( *vap ) [sl.start() + n*sl.stride()];
     }
     
     NumT min()
     {
      std::valarray<NumT> row = (*vap)[sl];
      return row.min();
     }
     
     NumT max()
     {
      std::valarray<NumT> row = (*vap)[sl];
      return row.max();
     }
     
     double mean()
     {
        std::valarray<NumT> row = (*vap)[sl];
          return
               static_cast<double> ( row.sum() )
               /
               static_cast<double> ( row.size() );
     }
};

template <typename NumT>
class Matrix {
private:
     std::valarray<NumT> *vap;
     int nor, noc;
public:
     Matrix ( int nor, int noc ) : nor ( nor ), noc ( noc ), vap ( new std::valarray<NumT> ( nor*noc ) ) {}
     ~Matrix()
     {
          delete vap;
     }

     Slice_iter<NumT> row ( int n )
     {
          return Slice_iter<NumT> ( vap, std::slice ( n*noc, noc, 1 ) );
     }

     Slice_iter<NumT> operator[] ( int n )
     {
          return row ( n );
     }

     double mean()
     {

          return
               static_cast<double> ( ( *vap ).sum() )
               /
               static_cast<double> ( ( *vap ).size() );
     }

};


typedef boost::adjacency_list < boost::listS, boost::vecS, boost::directedS,
        boost::property < boost::vertex_name_t, std::string,
        boost::property<boost::vertex_index2_t, int >> ,
        boost::property<boost::edge_weight_t, int >> ZLWGraph;

typedef boost::graph_traits<ZLWGraph>::vertex_descriptor ZLWGraphVertex;
typedef boost::property_map<ZLWGraph, boost::vertex_name_t>::type VertexNameMap;
typedef boost::property_map<ZLWGraph, boost::vertex_index2_t>::type VertexIndexMap;

void print_graph ( ZLWGraph &city_graph, VertexNameMap &v2str, std::string &city )
{

     std::fstream graph_log ( city + ".dot" , std::ios_base::out );
     boost::write_graphviz ( graph_log, city_graph, boost::make_label_writer ( v2str ) );

}

int main()
{

     std::string path {"genomes"};

     auto di = std::filesystem::directory_iterator ( path );
     int size = std::distance ( std::filesystem::begin ( di ), std::filesystem::end ( di ) );

     //std::cout << size << std::endl;

     Matrix<double> m {size, size};

     int r {0}, c {0};

     for ( const auto & entry1: std::filesystem::directory_iterator ( path ) ) {

         std::cout << entry1.path().string() << " ";
         
          for ( const auto & entry2: std::filesystem::directory_iterator ( path ) ) {

               ZLWTree<char, '/', '0'> zlwBt;

               std::ifstream ifs1 {entry1.path().string() };
               std::istreambuf_iterator<char> isbi1End;

               std::ifstream ifs2 {entry2.path().string() };
               std::istreambuf_iterator<char> isbi2End;

               for ( std::istreambuf_iterator<char> isbi1 {ifs1}, isbi2 {ifs2};
                         isbi1 != isbi1End || isbi2 != isbi2End;
                   ) {

                    if ( isbi1 != isbi1End ) {
                         zlwBt << *isbi1;
                         ++isbi1;
                    }
                    if ( isbi2 != isbi2End ) {
                         zlwBt << *isbi2;
                         ++isbi2;
                    }

               }

               zlwBt.eval();
               std::cout << std::setw ( 8 ) << ( m[r][c] = zlwBt.getVar() );
               ++c;
          }

          ++r;
          c=0;
          std::cout << std::endl;
     }

     std::cout << std::endl;
     std::cout << std::endl;

     double mean = m.mean();

     Matrix<int> gm {size, size};

     for ( int i {0}; i<size; ++i ) {
          
          std::cout << m[i].min() << " ";
          for ( int j {0}; j<size; ++j ) {
               if ( m[i][j] < mean )
                    gm[i][j] = 1;
               else
                    gm[i][j] = 0;

               std::cout << gm[i][j] << " ";
          }

          std::cout << std::endl;
     }




     ZLWGraph zlw_graph;
     VertexNameMap v2str = boost::get ( boost::vertex_name, zlw_graph );
     VertexIndexMap v2idx = boost::get ( boost::vertex_index2, zlw_graph );
     std::map <std::string, ZLWGraphVertex> str2v;


     int idx {0};
     for ( const auto & entry1: std::filesystem::directory_iterator ( path ) ) {

          ZLWGraphVertex v = boost::add_vertex ( zlw_graph );
          v2str[v] = entry1.path().string();
          v2idx[v] = idx++;
          str2v[entry1.path().string()] = v;
     }


     r = c = 0;
     for ( const auto & entry1: std::filesystem::directory_iterator ( path ) ) {

          for ( const auto & entry2: std::filesystem::directory_iterator ( path ) ) {

               if ( m[r][c] < mean )
                    boost::add_edge ( str2v[entry1.path().string()], str2v[entry2.path().string()], 1, zlw_graph );

               ++c;
          }
          ++r;
          c=0;

     }

     std::string fname {"teszt"};
     print_graph ( zlw_graph, v2str, fname );


}

