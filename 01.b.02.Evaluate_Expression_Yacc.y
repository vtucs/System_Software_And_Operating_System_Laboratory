/*
1.b. Write YACC program to evaluate arithmetic expression involving operators: +, -, *, and /

YACC Part
*/

%{
    #include <stdio.h>
    #include <stdlib.h>
    int yylex();
    int yyerror();
%}

%left '+' '-'
%left '*' '/'
%token NUM

%%

expr: stmt {printf("Result=%d\n", $1);}
    ;
stmt: stmt '+' stmt {$$ = $1 + $3;}
    | stmt '-' stmt {$$ = $1 - $3;}
    | stmt '*' stmt {$$ = $1 * $3;}
    | stmt '/' stmt {if($3 != 0) {$$ = $1 / $3;} else {yyerror();}}
    | NUM {$$ = $1;}
    ;

%%

void main()
{
    if(yyparse() == 0)
    {
        // Do Nothing, Result is already printed
    }
    else
    {
        yyerror();
    }
}

int yyerror()
{
    printf("Invalid Expression\n");
    exit(0);
}

/*
Output:
>>> lex 01.b.01.Evaluate_Expression_Lex.l 
>>> yacc -d 01.b.02.Evaluate_Expression_Yacc.y 
>>> gcc lex.yy.c y.tab.c -ll -ly
>>> ./a.out 
2+3*8
Result=26
*/

