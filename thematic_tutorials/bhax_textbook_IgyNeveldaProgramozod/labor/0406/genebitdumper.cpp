#include <iostream>
#include <iterator>
#include <bitset>
#include <algorithm>


int main()
{
  std::istream_iterator<char> isiEnd, isi(std::cin);
  std::ostream_iterator<std::bitset<8>> osi(std::cout);
  std::copy(isi,isiEnd,osi);
  return 0;
}