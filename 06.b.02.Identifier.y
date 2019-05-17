/*
6.b. Write YACC program to recognize valid identifier, operators and keywords in the
given text (C program) file.
*/

%{
#include <stdio.h>
#include <stdlib.h>
int yylex();
int yyerror();
extern FILE *yyin;

int idCount=0, digitCount=0, keyCount=0, opCount=0;
%}

%token DIGIT IDENTIFIER KEYWORD OPERATOR
%%

input:
	DIGIT input { digitCount++; }
	| IDENTIFIER input { idCount++; }
	| KEYWORD input { keyCount++; }
    | OPERATOR input {opCount++;}
    | DIGIT { digitCount++; }
	| IDENTIFIER  { idCount++; }
	| KEYWORD { keyCount++; }
    | OPERATOR { opCount++;}
	;

%%

void main(int argc, char *argv[]) {
	
	if(argc != 2)
    {
        printf("usage: %s <src file>\n",argv[0]);
        return;
    }

    FILE *myfile = fopen(argv[1], "r");

	yyin = myfile;
	do
    {
		yyparse();
	} while (!feof(yyin));
    printf("numbers = %d\nKeywords = %d\nIdentifiers = %d\noperators = %d\n", digitCount, keyCount,idCount, opCount);
}

int yyerror()
{
	printf("Error");
	exit(-1);
}