% HACK ASSEMBLY LANGUAGE
% Each line is interpreted as an instruction
% There are two kinds of instructions:
%  - Load    (L instruction)
%  - Compute (C instruction)
% Load Instruction
%   @Xxx where Xxx can be any symbol (variable, label)
%   or a constant (it must be representable with a 15 bit wide unsigned int)
% Compute Instruction
%   Follows one of the following patterns:
%     [DST]=[CMP];[JMP]
%     [DST]=[CMP]
%     [CMP];[JMP]
%
% [CMP] defines the computation to be done
%
% [JMP] defines a condition which must be evaluated.
%         All conditions mean a comparison between the current calculation result and 0.
%         (Example D-1;JEQ will cause a jump if D is 1)
%					if the condition is met, a jump will occur.
%					else no jump will occur (program counter will be incremented by 1).
%         If a jump occurs, the next instruction is read from the ROM
%         from the address specified by register A value.
%
% [DST] defines where to output the calculation's result.
%       'M' means that the value will be stored on RAM and the address is specified by register A's value.
%        A means that the value will be stored to register A
%        D means that the value will be stored to register D
% Opcode fields
% [CMP] [JMP] [DST]
% 0      JGT   M
% 1      JEQ   D
% -1     JGE   MD
% D      JLT   A
% A      JNE   AM
% !D     JLE   AD
% !A     JMP   AMD
% -D
% -A
% D+1
% A+1
% D-1
% A-1
% D+A
% D-A
% A-D
% D&A
% D|A
% M
% !M
% -M
% M+1
% M-1
% D+M
% D-M
% M-D
% D&M
% D|M
%
% Shut down is under construction...
% Currently if you set RAM[32767] to 32767 (32767 is the last addressable)
% the interpreter will break the loop and exit.
% @32767
% D=A
% @32767
% M=D
%
% Below is a really simple loop.
% It goes through RAM[10] to RAM[1]
% and writes the double of the value of the ram address to
% the ram.
% Aka 20 to RAM[10], 18 to RAM[9] etc.
% This finishes in 55 cycles.
@10
D=A
(LOOP)
	A=D
	M=D+A
	D=D-1
	@LOOP
	D;JGT
(SHUTDOWN)
	@32767
	D=A
	@32767
	M=D