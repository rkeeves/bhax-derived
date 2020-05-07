#include <iostream>
#include <iterator>
#include <bitset>
#include <algorithm>

int main(int argc, char** argv)
{
  std::istream_iterator<char> isiEND, isi(std::cin); 
  std::ostream_iterator<std::bitset<8>> osi(std::cout);
  std::copy(isi, isiEND,osi);
	return 0;
}