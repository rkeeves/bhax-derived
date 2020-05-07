/*
 BHAX 391 adás - Lab4.: Matrix design init hack, COVID19 - Corona Em. Brdc. - Stay Home
 https://www.twitch.tv/videos/588138046
 https://youtu.be/oLErhmTA2Z0
 
 BHAX 407 adás - Lab5.: Matrix design/2 init hack, COVID19 - Corona Em. Brdc. - Stay Home
 lásd még Stroustrup könyv, Kiskapu, 2001, II. kötet, 906. o.
 https://www.twitch.tv/videos/593713517
 https://youtu.be/dxsYdX0RoDM
 */

#include "z3a18qa5_from_scratch.h"

#include <fstream>
#include <iomanip>
#include <filesystem>
#include <valarray>

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

};

int main()
{

     std::string path {"genomes"};

     auto di = std::filesystem::directory_iterator ( path );
     int size = std::distance ( std::filesystem::begin ( di ), std::filesystem::end ( di ) );

     //std::cout << size << std::endl;

     Matrix<double> m {size, size};

     int r {0}, c {0};

     for ( const auto & entry1: std::filesystem::directory_iterator ( path ) ) {

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

     for ( int i {0}; i<size; ++i ) {

          for ( int j {0}; j<size; ++j )
               std::cout << m[i][j] << " ";

          std::cout << std::endl;
     }


}
