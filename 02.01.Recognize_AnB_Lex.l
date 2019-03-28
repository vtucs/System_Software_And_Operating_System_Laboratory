/*
2. Develop, Implement and Execute a program using YACC tool to recognize all strings
ending with b preceded by n a’s using the grammar a
n b (note: input n value)

LEX Part
*/

%{
    #include "y.tab.h"
%}

%%

a return A;
b return B;
. return yytext[0];
\n return 0;

%%
