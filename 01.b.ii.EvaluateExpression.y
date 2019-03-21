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