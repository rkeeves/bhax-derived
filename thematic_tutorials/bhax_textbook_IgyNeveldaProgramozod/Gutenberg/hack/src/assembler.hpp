#ifndef HACK_ASSEMBLER_H
#define HACK_ASSEMBLER_H

#include <vector>
#include <stdexcept>

#include "instruction.hpp"

namespace hack{

std::vector<uint16_t> assemble_hack(std::istream &is,uint16_t codeBeg = 0, uint16_t dataBeg=0x000f);

std::string disassemble_hack(uint16_t inst);

}/* namespace hack */
#endif