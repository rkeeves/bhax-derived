%option noyywrap
%{
#include <stdio.h>
#include <math.h>
int intnumbers = 0;
int floatnumbers = 0;
%}
DIGIT	[0-9]
%%
{DIGIT}+	    {++intnumbers; printf("[int=%s %i]", yytext, atoi(yytext));}
{DIGIT}+"."{DIGIT}*	{++floatnumbers; printf("[float=%s %f]", yytext, atof(yytext));}
"brexit"      { printf("Boris Johnson was called! Exiting EU!\n"); exit(0); }
%%
int
main ()
{
 yylex ();
 printf("The number of ints is %d\n", intnumbers);
 printf("The number of floats is %d\n", floatnumbers);
 return 0;
}

