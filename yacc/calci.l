%{
#include<stdio.h>
#include<math.h>
#include "y.tab.h"
%}
%%
[0-9]+	{yylval.dval=atoi(yytext);return NUMBER;}
[\n]		{return 0;}
[\t]		{;}
.		{return yytext[0];}
%%
int yywrap()
{
		return 1;
}
