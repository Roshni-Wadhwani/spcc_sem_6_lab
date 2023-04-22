%{
int cWords=0;
int cCharacters=0;
int cLines=0;
%}

%%
\n { cLines++; cCharacters++; }
[^ \t\n]+ { cWords++, cCharacters += yyleng; }
. { cCharacters ++; }
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
printf("No of characters = %d , No of words = %d , No of lines = %d",cCharacters,cWords,cLines);
return 0;
}