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
    if(rand() % door_count == 0){
      count_keptbet_won++;
    }else{
        if(rand() % (door_count-2) == 0)count_newbet_won++;
    }
  }
  std::cout<< "Monty Hall"<< std::endl;
  std::cout<< "Trial count " << trial_count << std::endl;
  std::cout << "Wins(first bet) " << count_keptbet_won << std::endl;
  std::cout << "Wins(next bet)  " << count_newbet_won << std::endl;
  std::cout << "Win Ratio " << ((count_newbet_won==0) ? 0.0 : (static_cast<double>(count_keptbet_won)/static_cast<double>(count_newbet_won)))<< std::endl;
  std::cout << "Sum (sanity check)" << count_keptbet_won+count_newbet_won << std::endl;
  return 0;
}