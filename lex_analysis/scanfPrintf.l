%{
int nPrintf=0;
int nScanf=0;
%}

%%
"printf"	{ECHO; nPrintf++;}
"scanf"	{ECHO; nScanf++;}
%%
int yywrap(void){
return 1;
}

int main(int argc, char *argv[]){
FILE *fp;
fp=fopen(argv[1],"r");
if(!fp){
printf("Error occured!");
exit(1);
}
yyin=fp;
yylex();
fclose(fp);
printf("Total number of printf statements: %d\n",nPrintf);
printf("Total number of scanf statements: %d\n",nScanf);
return 0;
}