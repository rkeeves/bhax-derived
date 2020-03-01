#include "assembler.hpp"

#include <sstream>
#include <algorithm>
#include <string>
#include <bitset>
#include <iostream>
#include <map>
#include <set>

namespace hack{

std::string find_by_val(const std::map<std::string, uint16_t> &map, uint16_t value)
{
	auto it = map.begin();
	while(it != map.end()){
		if(it->second == value){
			return it->first;
		}
		it++;
	}
	return std::string();
}

static std::map<std::string, uint16_t> HACK_CMP_MASKS =
{
	{"0", 		0b0000101010000000},
	{"1", 		0b0111111000000},
	{"-1", 		0b0111010000000},
	{"D", 		0b0001100000000},
	{"A", 		0b0110000000000},
	{"!D", 		0b0001101000000},
	{"!A", 		0b0110001000000},
	{"-D", 		0b0001111000000},
	{"-A", 		0b0110011000000},
	{"D+1", 	0b0011111000000},
	{"A+1", 	0b0110111000000},
	{"D-1", 	0b0001110000000},
	{"A-1", 	0b0110010000000},
	{"D+A", 	0b0000010000000},
	{"D-A", 	0b0010011000000},
	{"A-D", 	0b0000111000000},
	{"D&A", 	0b0000000000000},
	{"D|A", 	0b0010101000000},
	{"M", 		0b1110000000000},
	{"!M", 		0b1110001000000},
	{"-M", 		0b1110011000000},
	{"M+1", 	0b1110111000000},
	{"M-1", 	0b1110010000000},
	{"D+M", 	0b1000010000000},
	{"D-M", 	0b1010011000000},
	{"M-D", 	0b1000111000000},
	{"D&M", 	0b1000000000000},
	{"D|M", 	0b1010101000000}
};

const uint16_t DST_NUL 	= 0b000000;
const uint16_t DST_M 		= 0b001000;
const uint16_t DST_D 		= 0b010000;
const uint16_t DST_MD 	= 0b011000;
const uint16_t DST_A 		= 0b100000;
const uint16_t DST_AM 	= 0b101000;
const uint16_t DST_AD 	= 0b110000;
const uint16_t DST_AMD 	= 0b111000;


static std::map<std::string, uint16_t> HACK_DST_MASKS =
{
	{"null", 	DST_NUL},
	{"M",			DST_M},
	{"D",			DST_D},
	{"MD",		DST_MD},
	{"A",			DST_A},
	{"AM",		DST_AM},
	{"AD",		DST_AD},
	{"AMD",		DST_AMD}
};

const uint16_t JMP_NUL = 0b000;
const uint16_t JMP_JGT = 0b001;
const uint16_t JMP_JEQ = 0b010;
const uint16_t JMP_JGE = 0b011;
const uint16_t JMP_JLT = 0b100;
const uint16_t JMP_JNE = 0b101;
const uint16_t JMP_JLE = 0b110;
const uint16_t JMP_JMP = 0b111;

static std::map<std::string, uint16_t> HACK_JMP_MASKS =
{
	{"null",	JMP_NUL},
	{"JGT", 	JMP_JGT},
	{"JEQ",		JMP_JEQ},
	{"JGE",		JMP_JGE},
	{"JLT",		JMP_JLT},
	{"JNE",		JMP_JNE},
	{"JLE",		JMP_JLE},
	{"JMP",		JMP_JMP}
};


uint16_t join_masks(uint16_t cmp, uint16_t dst, uint16_t jmp ){
		uint16_t code = 0b1110000000000000;
		code = ( code | cmp );
		code = ( code | dst );
		code = ( code | jmp );
		return code;
}

inline bool has_mask(uint16_t code, uint16_t msk ){
		return ( (code & msk) == msk );
}


static std::map<std::string, uint16_t> HACK_BASE_SYMBOLMAP =
{
	{"SP", 		0x00},
	{"LCL", 	0x01},
	{"ARG", 	0x02},
	{"THIS", 	0x03},
	{"THAT", 	0x04},
	{"R0", 		0x00},
	{"R1", 		0x01},
	{"R2", 		0x02},
	{"R3", 		0x03},
	{"R4", 		0x04},
	{"R5", 		0x05},
	{"R6", 		0x06},
	{"R7", 		0x07},
	{"R8", 		0x08},
	{"R9", 		0x09},
	{"R10", 	0x0a},
	{"R11", 	0x0b},
	{"R12", 	0x0c},
	{"R13", 	0x0d},
	{"R14", 	0x0e},
	{"R15", 	0x0f},
	{"SCREEN",0x4000},
	{"KBD", 	0x6000}
};

bool is_delim(char c)
{
	static std::set<char> DELIMS = {' ', '\t', '\r','=', '@', ';', '(',')','%'};
	return (DELIMS.find(c) != DELIMS.end());
}

bool is_delim_discardable(char c)
{
	static std::set<char> DISCARDABLES = {' ', '\t', '\r','%'};
	return (DISCARDABLES.find(c) != DISCARDABLES.end());
}

inline bool is_delim_terminator(char c)
{
	return (c == '%');
}

std::vector<std::string> tok(const std::string &s)
{
	std::vector<std::string> v;
	size_t from = 0;
	size_t len = 0;
	size_t sz = s.size();
	for(size_t i = 0; i < sz; ++i){
		if(is_delim(s[i])){
			if(len > 0){ v.push_back(s.substr(from,len));}
			len=0; from=i+1;
			if(! is_delim_discardable(s[i])){v.push_back(std::string(1,s[i]));}
			if(is_delim_terminator(s[i])){break;}				
		}else{
			len++;
		}
	}
	if( len > 0){v.push_back(s.substr(from,len));}
	return v;
}


uint16_t qry_mask(const std::string &s, const std::map<std::string, uint16_t>& m, const std::string &ty)
{
	auto it = m.find(s);
	if(it!=m.end()){ return it->second;}
	throw std::runtime_error(std::string("Undefined ")+ty+" '"+s+"'");
}


uint16_t qry_cmp(const std::string &s){return qry_mask(s,HACK_CMP_MASKS,"cmp");}


uint16_t qry_dst(const std::string &s){return qry_mask(s,HACK_DST_MASKS,"dst");}


uint16_t qry_jmp(const std::string &s){return qry_mask(s,HACK_JMP_MASKS,"jmp");}


enum class CommandKind{
	A_CMD, 	// Addressing for @Xxx where Xxx is either a symbol or a decimal number
	C_CMD, 	// for dest=comp; jump
	L_CMD,	// for (Xxx) where Xxx is a symbol
};

struct CommandToken{
public:
	CommandToken(CommandKind kindv) : kind(kindv), dst(0), cmp(0), jmp(0), sym(){}
	
	CommandToken(CommandKind kindv, uint16_t dstv, uint16_t cmpv, uint16_t jmpv) : kind(kindv), dst(dstv), cmp(cmpv), jmp(jmpv), sym(){}
	
	CommandToken(CommandKind kindv, std::string symv) : kind(kindv), dst(0), cmp(0), jmp(0), sym(symv){}
	
	CommandKind kind;
	uint16_t dst,cmp,jmp;
	std::string sym;
	
};


CommandToken parse_command(const std::vector<std::string> &tokens)
{
	if(tokens.size() == 2 && tokens[0] == "@"){ 
		 return CommandToken(CommandKind::A_CMD, tokens[1]);
	}
	if(tokens.size() == 3 && tokens[0] == "(" && tokens[2] == ")"){ 
		return CommandToken(CommandKind::L_CMD, tokens[1]);
	}
	if(tokens.size() == 5 && tokens[1] == "=" && tokens[3] == ";"){ 
		return CommandToken(CommandKind::C_CMD, qry_dst(tokens[0]),qry_cmp(tokens[2]),qry_jmp(tokens[4]));
	}
	if(tokens.size() == 3 && tokens[1] == ";"){ 
		return CommandToken(CommandKind::C_CMD, DST_NUL,qry_cmp(tokens[0]),qry_jmp(tokens[2]));
	}
	if(tokens.size() == 3 && tokens[1] == "="){ 
		return CommandToken(CommandKind::C_CMD, qry_dst(tokens[0]),qry_cmp(tokens[2]),JMP_NUL);
	}
	throw std::runtime_error("Unrecognized command pattern");
}

const std::string ASM_DELIMS = "=";
std::vector<CommandToken> parse(std::istream &is)
{
	std::vector<CommandToken> cmds;
	std::string line;
	int lineCounter = 0;
	while(std::getline(is, line)){
		lineCounter++;
		std::vector<std::string> tokens = tok(line);
		try{
			if(! tokens.empty()){cmds.push_back(parse_command(tokens));}
		}catch(const std::exception &e){
			std::stringstream ss;
			ss << "Invalid token at line "<< lineCounter<< " : " << e.what();
			throw std::runtime_error(ss.str());
		}
		
	}
	return cmds;
}

using SymbolMap = std::map<std::string,uint16_t>;

void process_labels(const std::vector<CommandToken> &toks, SymbolMap& symMap, uint16_t firstAddress)
{
	uint16_t next_instr_idx =0;
	for(std::vector<CommandToken>::const_iterator it = toks.begin(); it != toks.end(); ++it)
	{
		if(it->kind == CommandKind::A_CMD || it->kind == CommandKind::C_CMD ){
			next_instr_idx++;
		}else if(it->kind == CommandKind::L_CMD){
			symMap[it->sym] = (firstAddress+next_instr_idx);
		}
	}
}


bool is_number(const std::string& s)
{
    return !s.empty() && std::find_if(s.begin(), s.end(), [](char c) { return !std::isdigit(c); }) == s.end();
}


void process_variables(std::vector<CommandToken> &toks, SymbolMap& symMap, uint16_t firstAddress)
{
	uint16_t var_count =0;
	for(auto it = toks.begin(); it != toks.end(); ++it){
		if(it->kind == CommandKind::A_CMD && !( is_number(it->sym)) ){
			auto e = symMap.find(it->sym);
			if(e == symMap.end()){
				symMap[it->sym] = (firstAddress+var_count);
				var_count++;
			}
		}
	}
}


std::vector<uint16_t> translate_to_bin(const SymbolMap& symMap, const std::vector<CommandToken> &toks)
{
	std::vector<uint16_t> v;
	for(std::vector<CommandToken>::const_iterator it = toks.begin(); it != toks.end(); ++it)
	{
		if(it->kind == CommandKind::A_CMD){
			auto e = symMap.find(it->sym);
			if(e != symMap.end()){
				v.push_back(e->second);
			}else{
				try{
					v.push_back(std::stoi(it->sym));
				}catch(...){
					throw std::runtime_error("Failed to convert raw address: " + it->sym);
				}
			}
		}else if(it->kind == CommandKind::C_CMD){
			v.push_back(join_masks(it->cmp,it->dst,it->jmp));
		}
	}
	return v;
}


std::vector<uint16_t> assemble_hack(std::istream &is,uint16_t codeBeg, uint16_t dataBeg)
{
	if (!is){throw std::runtime_error("Inpustream err");}
	SymbolMap symMap(HACK_BASE_SYMBOLMAP);
	auto toks = parse(is);
	process_labels(toks,symMap,codeBeg);
	process_variables(toks,symMap,dataBeg);
	return translate_to_bin(symMap,toks);
}
const uint16_t FIELD_MASK_CMP   = 0b0001111111000000;
const uint16_t FIELD_MASK_DST   = 0b0000000000111000;
const uint16_t FIELD_MASK_JMP   = 0b0000000000000111;
std::string disassemble_hack(uint16_t inst)
{
	std::stringstream ss;
	if(has_flag(inst,Flags::TY_C)){
		uint16_t fld = bitfield(inst,HackBitFields::DST);
		if(DST_NUL != fld){ss << find_by_val(HACK_DST_MASKS,fld) << "=";}
		fld = bitfield(inst,HackBitFields::CMP);
		ss << find_by_val(HACK_CMP_MASKS,fld);
		fld = bitfield(inst,HackBitFields::JMP);
		if(JMP_NUL != fld){ss <<";" << find_by_val(HACK_JMP_MASKS,fld);}
	}else{
		ss << '@' << inst;
	}
	return ss.str();
}
}/* end of namespace hack */