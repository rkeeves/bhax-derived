#include "chip.hpp"

namespace hack{
// [LIFECYCLE]
Chip::Chip() noexcept :
	rom(HACK_MAX_MEM_SIZE), 
	ram(HACK_MAX_MEM_SIZE),
	pc(0),a(0),d(0),inst(0)
{}

Chip::~Chip(){}

// [ACCESSORS]
uint16_t Chip::peek_pc() const { return pc; }


uint16_t Chip::peek_a() const { return a; }


uint16_t Chip::peek_d() const { return d; }


uint16_t Chip::peek_inst() const{ return inst; }


const std::vector<uint16_t>& Chip::peek_ram() const { return ram; }

uint16_t Chip::peek_ram_by_adr(size_t adr) const { return ram.at(adr); }

// [MODIFIERS]
void Chip::load(const std::vector<uint16_t> &program)
{
	pc=a=d=inst= 0;
	int sz = program.size();
	if(sz > HACK_MAX_MEM_SIZE){throw std::runtime_error("Program is too large for ROM");}
	for(int i = 0; i < sz; ++i){rom[i] = program[i];}
	inst = rom[pc];
}


void Chip::tick()
{
	// TODO: memory mapped device input handling will come here
	do_cycle();
	// TODO: memory mapped device ouput handling will come here
}

// [INTERNALS]
void Chip::do_cycle()
{
	if(has_flag(inst,Flags::TY_C)){
		uint16_t out = compute();
		store(out);
		jump(out);
	}else{
		a = bitfield(inst,HackBitFields::ADR);
		pc++;
	}
	inst = rom[pc];
}


uint16_t Chip::compute()
{
	uint16_t o = 0;
	uint16_t x = d;
	x = (has_flag(inst,Flags::CMP_ZX)) ? 0 : x;
	x = (has_flag(inst,Flags::CMP_NX)) ? (~x) : x;
	uint16_t y = (has_flag(inst,Flags::CMP_A)) ? ram[a] : a;
	y = (has_flag(inst,Flags::CMP_ZY)) ? 0 : y;
	y = (has_flag(inst,Flags::CMP_NY)) ? (~y) : y;
	o = (has_flag(inst,Flags::CMP_F)) ? (x+y) : (x&y);
	o = (has_flag(inst,Flags::CMP_NO)) ? !o : o;
	return o;
}


void Chip::store(uint16_t out)
{
	if(has_flag(inst,Flags::DST_A))	{ a = out;}
	if(has_flag(inst,Flags::DST_D))	{ d = out;}
	if(has_flag(inst,Flags::DST_M))	{ ram[a] = out;}
}


void Chip::jump(uint16_t out)
{
		bool j1 = (has_flag(inst,Flags::JMP_L)) ? has_flag(out,Flags::TY_C) : false;
		bool j2 = (has_flag(inst,Flags::JMP_E)) ? out==0 : false;
		bool j3 = (has_flag(inst,Flags::JMP_G)) ? out>0  : false;
		pc = (j1||j2||j3) ? a : (pc+1);
}
} /* namespace hack */
	