%{
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
int yyerror(char *err);
int yylex();
char * createT();
int count=0;
%}
%union		{char str[30];}
%left '+'
%left '-'
%left '*'
%left '/'
%token <str> Var
%token <str> Num
%type <str> s
%type <str> exp
%%
s		:	Var '=' exp		{printf("\n%s=%s\n",$1,$3);}
exp		:	'('exp')'		{strcpy($$,$2);}
		|	exp '+' exp		{strcpy($$,createT());printf("\n%s=%s+%s",$$,$1,$3);}
		|	exp '-' exp		{strcpy($$,createT());printf("\n%s=%s-%s",$$,$1,$3);}
		|	exp '*' exp		{strcpy($$,createT());printf("\n%s=%s*%s",$$,$1,$3);}
		|	exp '/' exp		{strcpy($$,createT());printf("\n%s=%s/%s",$$,$1,$3);}
		|	Num			{strcpy($$,$1);}
		|	Var			{strcpy($$,$1);}
		;
%%

char * createT()
{ 
	char snum[30],*ptr;
	sprintf(snum,"t%d",count);
	ptr=snum;
	count++;
	return ptr;
}

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