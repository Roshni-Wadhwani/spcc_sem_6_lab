%{
int cKeywords=0;
int cIdentifiers=0;
int cOperators=0;
%}

%%
"int"|"return"|"void"|"main"  	{cKeywords++;}
[a-zA-Z0-9_]*				{cIdentifiers++;}
"+"|"="					{cOperators++;}
. ;
%%

int yywrap(void){
return 1;
}

int main(int argc,char *argv[])
{
FILE *fp;
fp=fopen(argv[1],"r");
if(!fp){
	printf("Error occured");
	exit(1);
}
yyin=fp;
yylex();
fclose(fp);
printf("----Reading Input from file %s-----\n\n",argv[1]);
printf("No of keywords = %d , No of Identifiers = %d , No of Operators = %d",cKeywords,cIdentifiers,cOperators);
return 0;
}