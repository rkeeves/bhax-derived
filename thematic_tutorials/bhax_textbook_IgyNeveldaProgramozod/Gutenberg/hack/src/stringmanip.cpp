#include "stringmanip.hpp"

#include <algorithm>

namespace hack{


std::string trim_front(const std::string& str, const std::string& trimmables)
{
	auto beg = str.find_first_not_of(trimmables);
	if (beg == std::string::npos){return "";}
  return str.substr(beg);
}

std::string trim_back(const std::string& str, const std::string& trimmables)
{
	const auto end = str.find_last_not_of(trimmables);
  return (end == std::string::npos) ? str : str.substr( 0 , (end + 1) );
}

std::string trim(const std::string& str, const std::string& trimmables)
{
    const auto beg = str.find_first_not_of(trimmables);
    if (beg == std::string::npos){return "";}
    const auto end = str.find_last_not_of(trimmables);
    const auto rng = end - beg + 1;
    return str.substr(beg, rng);
}

std::vector<std::string> split(const std::string& str, const std::string& delims, bool keepDelims)
{
    std::vector<std::string> result;
    size_t pos = 0, lastPos = 0;
    while ((pos = str.find_first_of(delims, lastPos)) != std::string::npos)
    {
			if(pos-lastPos > 0)result.push_back(str.substr(lastPos, pos-lastPos));
			if(keepDelims) result.push_back(std::string(1,str[pos]));
      lastPos = pos+1;
    }
    result.push_back(str.substr(lastPos));
    return result;
}

} /* namespace hack */