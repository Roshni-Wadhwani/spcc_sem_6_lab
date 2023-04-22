%{
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
int yyerror(char *err);
int yylex();
%}
%union
{
float dval;
}
%token <dval> NUMBER
%left '+''-'
%left '*''/'
%nonassoc UMINUS
%type <dval> exp
%%

state:exp {printf("Answer:%f",$1);}
;
exp:	NUMBER
	|exp'+'exp			{$$=$1+$3;}
	|exp'-'exp			{$$=$1-$3;}
	|exp'*'exp			{$$=$1*$3;}
	|exp'/'exp			{$$=$1/$3;}
	|'('exp')'			{$$=$2;}
	|'-'exp %prec UMINUS	{$$=-$2;}
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
