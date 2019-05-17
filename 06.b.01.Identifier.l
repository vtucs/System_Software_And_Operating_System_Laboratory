%{
#include <stdio.h>
#include "y.tab.h"
extern int yylval;
%}

%%

[ \t] ;
[+\-*/=<>] {printf("operator is %s\n",yytext);return OPERATOR;}
[0-9]+ {yylval = atoi(yytext); printf("numbers is %d\n",yylval); return DIGIT;}
int|char|bool|float|void|for|do|while|if|else|return|void {printf("keyword is %s\n",yytext);return KEYWORD;}
[a-zA-Z0-9]+ {printf("identifier is %s\n",yytext);return IDENTIFIER;}
. ;
%%