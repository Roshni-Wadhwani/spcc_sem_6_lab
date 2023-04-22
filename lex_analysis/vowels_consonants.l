%{
int cVowels=0;
int cConsonants=0;
%}

%%
[a|e|i|o|u|A|E|I|O|U]	{cVowels++;}
[a-zA-Z]			{cConsonants++;}
.;
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
printf("No of consonants = %d , No of vowels = %d ",cConsonants,cVowels);
return 0;
}