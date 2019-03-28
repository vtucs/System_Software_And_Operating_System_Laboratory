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