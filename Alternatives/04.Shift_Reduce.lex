%{
    #include "y.tab.h"

%}
%%
[a-zA-Z][a-zA-Z0-9]* {printf("\nShift "); ECHO; return id;}
[*+] {printf("\nShift "); ECHO; return yytext[0];}
[()] {printf("\nShift "); ECHO; return yytext[0];}
. ;
\n return 0;
%%
