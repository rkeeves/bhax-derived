#ifndef HACK_STRINGMANIP_H
#define HACK_STRINGMANIP_H

#include <vector>
#include <string>

namespace hack{


std::string trim_front(const std::string& str, const std::string& trimmables);

/**
 * Returns a substring which starts at the original string begin,
 * and ends at the last non-trimmable char.
 * If the operation cannot be done (e.g. string of size 0, string with only trimmables)
 * it returns an empty string.
 * 
 * @param str source string
 * @param trimmables trimmable chars as string
 * @return resulting stirng
 */
std::string trim_back(const std::string& str, const std::string& trimmables);

/**
 * Returns a substring which starts at the first non-trimmable char,
 * and ends at the last non-trimmable char.
 * If the operation cannot be done (e.g. string of size 0, string with only trimmables)
 * it returns an empty string.
 * 
 * @param str source string
 * @param trimmables trimmable chars as string
 * @return resulting stirng
 */
std::string trim(const std::string& str, const std::string& trimmables);

/**
 * Returns a vector containing the substrings after the string was split.
 * 
 * @param str source string
 * @param delims delimiter chars as string
 * @param keepDelims if true, pushes the delimiters as separate elements into the vector
 * @return resulting stirng
 */
std::vector<std::string> split(const std::string& str, const std::string& delims, bool keepDelims = true);
}/* end of namespace hack */
#endif