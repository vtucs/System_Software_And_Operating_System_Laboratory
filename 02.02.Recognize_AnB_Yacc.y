/*
2. Develop, Implement and Execute a program using YACC tool to recognize all strings
ending with b preceded by n a’s using the grammar a
n b (note: input n value)

YACC Part
*/

%{
    #include <stdio.h>
    #include <stdlib.h>
    int count=0;
    int yylex();
    int yyerror();
%}

%token A B
%%

str: T B
    ;
T: A T {++count;}
    |
    ;

%%

void main()
{
    int n;
    printf("Enter n value\n");
    scanf("%d\n", &n);

    if(yyparse() == 0 && n == count)
    {
        printf("Valid String\n");
    }
    else
    {
        yyerror();
    }
}

int yyerror()
{
    printf("Invalid String\n");
}

/*
Output:

>>> lex 02.01.Recognize_AnB_Lex.l 
>>> yacc -d 02.02.Recognize_AnB_Yacc.y 
>>> gcc lex.yy.c y.tab.c -ll -ly
>>> ./a.out 
Enter n value
5
aaaaab
Valid String
*/

