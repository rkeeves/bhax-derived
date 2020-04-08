#include <iostream>
#include <vector>
#include <iterator>


int main()
{
  std::vector<int> v;
  v.reserve(5);
  v.push_back(1);
  v.push_back(2);
  v.push_back(3);
  v.push_back(4);
  v.push_back(5);
  for(std::vector<int>::iterator it = v.begin(); it != v.end(); ++it){
    std::cout << *it << " ";
  }
  
  
  return 0;
}