#include <iostream> 
#include <stdlib.h>

int main()
{
  const int trial_count = 100000;
  const int door_count = 3;
  int count_keptbet_won = 0;
  int count_newbet_won = 0;
  for (int i = 0; i < trial_count; i++)
  {
    // The else branch is needed for cases door_count>3
    //
    // there are door_count-1 goats and 1 car
    // we already know a goat which was shown by the show host
    // and because the code got to this point,
    // the first choice was bet
    // so we rebet on door_count-2 possibilities
    // which in the case of door_count=3 returns always true,
    // therefore could be optimized away
    // sorry I'm too https://youtu.be/vLLiM5tSqrw right now
    if(rand() % door_count == 0) count_keptbet_won++;
    else{
        
        if(rand() % (door_count-2) == 0)count_newbet_won++;
    }
      
  }
  std::cout<< "Narcos?! Plata o plomo, baby!"<< std::endl;
  std::cout<< "tamano de la muestra " << trial_count << std::endl;
  std::cout << "ganar (mantenida) " << count_keptbet_won << std::endl;
  std::cout << "ganar (eligio de nuevo) " << count_newbet_won << std::endl;
  std::cout << "proporcion " << ((count_newbet_won==0) ? 0.0 : (static_cast<double>(count_keptbet_won)/static_cast<double>(count_newbet_won)))<< std::endl;
  std::cout << "suma (prueba de cordura)" << count_keptbet_won+count_newbet_won << std::endl;
  return 0;
}