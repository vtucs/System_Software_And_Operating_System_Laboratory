/*
6.b. Write YACC program to recognize valid identifier, operators and keywords in the
given text (C program) file.

YACC PART
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
    printf("Numbers = %d\nKeywords = %d\nIdentifiers = %d\nOperators = %d\n", digitCount, keyCount,idCount, opCount);
}

int yyerror()
{
    printf("Error");
    exit(-1);
}

/*
Output:

>>> lex 06.b.01.Identifier_Lex.l 
>>> yacc -d 06.b.02.Identifier_Yacc.y 
>>> gcc lex.yy.c y.tab.c -ll -ly
>>> ./a.out input.6b.txt 
void is Keyword
main is Identifier


int is Keyword
a is Identifier
= is Operator
5 is Number

if is Keyword
a is Identifier
> is Operator
3 is Number


printf is Identifier
d is Identifier
a is Identifier



Numbers = 2
Keywords = 3
Identifiers = 6
Operators = 2

>>> cat input.6b.txt 
void main()
{
	int a = 5;
	if(a > 3)
	{
		printf("%d", a);
	}
}

*/
