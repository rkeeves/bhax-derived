#ifndef HACK_INSTRUCTION_H
#define HACK_INSTRUCTION_H

#include <cstdint>
#include <string>
#include <bitset>
#include <initializer_list>

namespace hack{

const uint16_t HACK_MAX_MEM_SIZE = 0b1000000000000000;

enum class Flags{
	TY_C 			= 15,
	CMP_A     = 12,
	CMP_ZX    = 11,
	CMP_NX    = 10,
	CMP_ZY    = 9,
	CMP_NY    = 8,
	CMP_F     = 7,
	CMP_NO    = 6,
	DST_A     = 5,
	DST_D     = 4,
	DST_M   	= 3,
	JMP_L   	= 2,
	JMP_E   	= 1,
	JMP_G   	= 0
};


inline bool has_flag(uint16_t v, Flags f)
{
	return (v & (1 << static_cast<int>(f)));
}


inline uint16_t set_flags(uint16_t source, std::initializer_list<Flags> l) {
	for(auto f : l){ source = source | (1 << static_cast<int>(f)) ;}
  return source;
}


enum class HackBitFields : uint16_t{
	ADR 	= 0b0111111111111111,
	CMP   = 0b0001111111000000,
	DST   = 0b0000000000111000,
	JMP   = 0b0000000000000111
};


inline uint16_t bitfield(uint16_t v, HackBitFields f)
{
	return (v & static_cast<uint16_t>(f));
}


inline std::string to_bitstring(uint16_t v)
{
	return std::bitset<16>(v).to_string();
}

const uint16_t RAM_SP     = 0x0000;
const uint16_t RAM_LCL    = 0x0001;
const uint16_t RAM_ARG    = 0x0002;
const uint16_t RAM_THIS   = 0x0003;
const uint16_t RAM_THAT   = 0x0004;
const uint16_t RAM_R0     = 0x0000;
const uint16_t RAM_R1     = 0x0001;
const uint16_t RAM_R2     = 0x0002;
const uint16_t RAM_R3     = 0x0003;
const uint16_t RAM_R4     = 0x0004;
const uint16_t RAM_R5     = 0x0005;
const uint16_t RAM_R6     = 0x0006;
const uint16_t RAM_R7     = 0x0007;
const uint16_t RAM_R8     = 0x0008;
const uint16_t RAM_R9     = 0x0009;
const uint16_t RAM_R10    = 0x000a;
const uint16_t RAM_R11    = 0x000b;
const uint16_t RAM_R12    = 0x000c;
const uint16_t RAM_R13    = 0x000d;
const uint16_t RAM_R14    = 0x000e;
const uint16_t RAM_R15    = 0x000f;
const uint16_t RAM_SCREEN = 0x4000;
const uint16_t RAM_KBD    = 0x6000;

}/* namespace hack */
#endif