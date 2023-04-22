%{
int nAdj=0;
int nAdv=0;
int nVerb=0;
int nNoun=0;
%}

%%

"beautiful"|"good"|"fast"|"handsome"	{ECHO;nAdj++;}
"never"|"always"|"now"|"today"		{ECHO;nAdv++;}
"speak"|"ran"|"dance"|"watch"|"smells"	{ECHO;nVerb++;}
"train"|"pizza"					{ECHO;nNoun++;}
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
printf("\nTotal number of adjectives: %d\n",nAdj);
printf("Total number of adverbs: %d\n",nAdv);
printf("Total number of verbs: %d\n",nVerb);
printf("Total number of nouns: %d\n",nNoun);
return 0;
}