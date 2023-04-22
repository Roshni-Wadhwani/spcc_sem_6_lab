%{
#include "y.tab.h"
%}

%%
[a-zA-Z]			{return ALPHABET;}
[0-9]				{return NUMBER;}
[_]				{return UNDERSCORE;}
[\n]				{return 0;}
.				{return yytext[0];}
%%
int yywrap()
{
		return 1;
}