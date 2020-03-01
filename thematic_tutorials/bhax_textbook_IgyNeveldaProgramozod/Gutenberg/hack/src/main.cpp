#include <iostream>
#include <fstream>
#include <iomanip>
#include <exception>

#include "chip.hpp"
#include "assembler.hpp"
#include "stringmanip.hpp"

using namespace hack;

void usage (void)
{
  std::cout << "Hack machine" << std::endl;
  std::cout << "Usage: cmd [IN_FILE] [MAX_CYCLES]" << std::endl;
  std::cout << std::endl;
  std::cout << "Options:" << std::endl;
  std::cout << "  " << " " << std::endl;
}

void diag(const Chip &chip, int cycle)
{
	std::cout 
		<< "["<< cycle <<"]"<< std::endl
		<< "I  : " <<  std::setw(9) << std::left << disassemble_hack(chip.peek_inst()) << " (" << to_bitstring(chip.peek_inst())  << ")" << std::endl
		<< "PC : " << chip.peek_pc() << std::endl
		<< "A  : "<< chip.peek_a() << std::endl
		<< "D  : " << chip.peek_d() << std::endl;
}

void peek_ram16(const Chip &chip)
{
	std::cout << "RAM: ";
	auto ram = chip.peek_ram();
	for(size_t x = 0; x < 4; ++x)
		for(size_t y = 0; y < 4; ++y)
			std::cout << ram[x*4+y] << " ";
	std::cout << std::endl;
}

int main(int argc, char** argv)
{	
	int max_cycles = 0;
	try{
		if(argc != 3){
			std::cerr << "[ARGS_ERROR]" << "Bad args!"<< std::endl;
			usage();
			return -1;
		}
		try{
			max_cycles = std::stoi( argv[2] );
		}catch(...){
			throw std::runtime_error("[ARGS_ERROR] Bad max_cycles option!");
		}
		if(max_cycles < 0){
			throw std::runtime_error("Bad max_cycles option!");
		}
		std::string in_fname = argv[1];
		std::fstream fin(in_fname, std::ios_base::in);
		if (!fin){
				std::cerr << "[EXEC_ERROR]" << "Input file '" << in_fname << "' does not exist!" << std::endl;
				usage ();
				return -2;
		}
		auto hackcode = assemble_hack(fin);
		fin.close();
		{
			Chip chip;
			chip.load(hackcode);
			for(int step = 0; step < max_cycles;++step)
			{
				chip.tick();
				std::cout << std::string(42,'=')<< std::endl;
				diag(chip,step);
				peek_ram16(chip);
				if(chip.peek_ram_by_adr(HACK_MAX_MEM_SIZE-1) == HACK_MAX_MEM_SIZE-1){
					break;
				};
			}
			std::cout << std::string(42,'=')<< std::endl;
		}
  }catch(const std::exception &e){
    std::cerr << "[EXEC_ERROR] " << e.what() << std::endl << std::endl;
    usage ();
    return -4;
  }catch(...){
    std::cerr << "[EXEC_ERROR] " << "Unknown error during execution!" << std::endl << std::endl;
    usage ();
    return -5;
  }
	return 0;
}