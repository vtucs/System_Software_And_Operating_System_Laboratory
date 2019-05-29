%{
#include<stdio.h>
#include<stdlib.h>
int yyerror();
%}
%token id
%left '+' '-'
%left '*' '/'
%%
exp: E {printf("\n Accept-Success");};
E: E'+'T {printf("\nReduce E -> E + T\n");}
 |T { printf("\nReduce E->T\n");}
 ;
T:T'*'F {printf("\nReduce T -> T * F\n");} 
 |F {printf("\nReduce T -> F\n");}
 ;
F:'('E')'{printf("\nReduce F -> ( E )\n");}
 |id {printf("\nReduce F->id\n");}
 ;
%%
int yyerror()
{
printf("error");
exit(0);
}
int main()
{
printf("Enter an expression:\n");
yyparse();
exit(0);
}
