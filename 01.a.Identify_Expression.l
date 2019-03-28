/*
1.a. Write a LEX program to recognize valid arithmetic expression. Identifiers in the
expression could be only integers and operators could be + and *. Count the
identifiers & operators present and print them separately. 
*/

%{
    int opnd = 0, ac = 0, sc = 0, mc = 0, dc = 0, valid = 0;
    void ext();
%}

%x OPER
%%

[a-zA-Z0-9]+ {valid = 1; opnd++; BEGIN OPER;}
<OPER>"+" {if(valid == 1) {valid = 0; ac++;} else {ext();}}
<OPER>"-" {if(valid == 1) {valid = 0; sc++;} else {ext();}}
<OPER>"*" {if(valid == 1) {valid = 0; mc++;} else {ext();}}
<OPER>"/" {if(valid == 1) {valid = 0; dc++;} else {ext();}}
<OPER>[a-zA-Z0-9]+ {if (valid == 0) {valid = 1; opnd++;} else {ext();}}
<OPER>. ext();
<OPER>\n return 0;
. ext();
\n return 0;

%%

void ext()
{
    printf("Invalid Expression");
    exit(0);
}
void main()
{
    printf("Enter Expression\n");
    yylex();
    printf("Valid Expression\n");
    printf("Count Operand=%d, Plus=%d, Minus=%d, Multiply=%d, Divide=%d\n", opnd, ac, sc, mc, dc);
}

/*
Output:

>>> lex 01.a.Identify_Expression.l 
>>> gcc lex.yy.c -ll
>>> ./a.out 
Enter Expression
a+b*c
Valid Expression
Count Operand=3, Plus=1, Minus=0, Multiply=1, Divide=0
*/

