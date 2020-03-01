#ifndef HACK_CHIP_H
#define HACK_CHIP_H

#include <vector>     // for modeling RAM
#include <stdexcept>  // for throwing std::runtime_error

#include "instruction.hpp"

namespace hack{
class Chip{
// [LIFECYCLE]
public:

	Chip() noexcept;
	
	~Chip();
	
private:

	Chip(const Chip &);
	
	Chip operator=(const Chip &);
// [ACCESSORS]
public:
	uint16_t peek_pc() const;
	
	uint16_t peek_a() const;
	
	uint16_t peek_d() const;
	
	uint16_t peek_inst() const;
	
	const std::vector<uint16_t>& peek_ram() const;
	
	uint16_t peek_ram_by_adr(size_t adr) const;
// [MODIFIERS]
public:

	void load(const std::vector<uint16_t> &program);
	
	void tick();
	
// [INTERNALS]
private:
	void do_cycle();

	uint16_t compute();

	void store(uint16_t out);

	void jump(uint16_t out);
// [STATE]
private:

	std::vector<uint16_t> rom;
	
	std::vector<uint16_t> ram;
	
	uint16_t pc;
	
	uint16_t a;
	
	uint16_t d;
	
	uint16_t inst;

};
} /* namespace hack */
#endif