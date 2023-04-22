%{
#include<stdlib.h>
#include<stdio.h>
int yyerror(char *err);
int yylex();
%}

%token ALPHABET NUMBER UNDERSCORE

%%
statement:variable {printf("Valid variable\n");exit(0);}
;
variable:ALPHABET extra
	  |UNDERSCORE extra	
;
extra:NUMBER extra
     |UNDERSCORE extra
     |ALPHABET extra
     |ALPHABET
     |NUMBER
     |UNDERSCORE	
;
%%

int main()
{
	yyparse();
	return 0;
}
int yyerror(char *err)
{
	printf("Invalid");
	exit(0);
}		
