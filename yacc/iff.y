%{
int count=0;
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
int yyerror(char *err);
int yylex();
%}
%token IF RELOP S NUMBER ID 
%%
statement:if_statement {printf("Number  of nested if statements is %d\n",count);exit(0);}
;
if_statement:IF'('cond')' '{'if_statement'}'  {count++;}
		|S
;
cond: x RELOP x
;
x :	ID
  |	NUMBER
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