#include <iostream> // std out
#include <cstdarg>  // var arg handling
#include <random>   // init weight matrices

class Perceptron
{
public:
  Perceptron ( int nof, ... )
  {
    n_layers = nof;
    units = new double*[n_layers];
    n_units = new int[n_layers];
    va_list vap;
    va_start ( vap, nof );
    for ( int i {0}; i < n_layers; ++i ){
      n_units[i] = va_arg ( vap, int );
      if ( i ){units[i] = new double [n_units[i]];}
    }
    va_end ( vap );
    weights = new double**[n_layers-1];
    std::default_random_engine gen;
    std::uniform_real_distribution<double> dist ( -1.0, 1.0 );
    for ( int i {1}; i < n_layers; ++i ){
      weights[i-1] = new double *[n_units[i]];
      for ( int j {0}; j < n_units[i]; ++j ){
        weights[i-1][j] = new double [n_units[i-1]];
        for ( int k {0}; k < n_units[i-1]; ++k )
        {
          weights[i-1][j][k] = dist ( gen );
        }
      }
    }
  }

  double sigmoid ( double x )
  {
    return 1.0/ ( 1.0 + exp ( -x ) );
  }


  double operator() ( double image [] )
  {
    units[0] = image;
    for ( int i {1}; i < n_layers; ++i ){
      for ( int j = 0; j < n_units[i]; ++j ){
        units[i][j] = 0.0;
        for ( int k = 0; k < n_units[i-1]; ++k ){
          units[i][j] += weights[i-1][j][k] * units[i-1][k];
        }
        units[i][j] = sigmoid ( units[i][j] );
      }
    }
    return sigmoid ( units[n_layers - 1][0] );
  }

  ~Perceptron(){
    for ( int i {1}; i < n_layers; ++i )
    {
      for ( int j {0}; j < n_units[i]; ++j ){
        delete [] weights[i-1][j];
      }
      delete [] weights[i-1];
    }
    delete [] weights;
    for ( int i {0}; i < n_layers; ++i )
    {
      if ( i ){delete [] units[i];}
    }
    delete [] units;
    delete [] n_units;
  }

private:
  Perceptron ( const Perceptron & );
  Perceptron & operator= ( const Perceptron & );

  int n_layers;
  int* n_units;
  double **units;
  double ***weights;

};

int main(){
  Perceptron p(3,3,2,1);
  double img[] = {0.0,0.9,0.7};
  double res = p(img);
  std::cout << res << std::endl;
}