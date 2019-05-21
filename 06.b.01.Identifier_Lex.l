/*
6.b. Write YACC program to recognize valid identifier, operators and keywords in the
given text (C program) file.

LEX PART
*/

%{
#include <stdio.h>
#include "y.tab.h"
extern int yylval;
%}

%%

[ \t] ;
[+\-*/=<>] {printf("%s is Operator\n",yytext);return OPERATOR;}
[0-9]+ {yylval = atoi(yytext); printf("%d is Number\n",yylval); return DIGIT;}
int|char|bool|float|void|for|do|while|if|else|return|void {printf("%s is Keyword\n",yytext);return KEYWORD;}
[a-zA-Z0-9]+ {printf("%s is Identifier\n",yytext);return IDENTIFIER;}
. ;
%%
