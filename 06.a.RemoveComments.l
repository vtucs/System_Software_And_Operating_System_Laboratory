%x CMNTML CMNTSL
%%

"/*" {BEGIN CMNTML;}
<CMNTML>. ;
<CMNTML>\n ;
<CMNTML>"*/" {BEGIN 0;}
"//" {BEGIN CMNTSL;}
<CMNTSL>. ;
<CMNTSL>\n {BEGIN 0;}

%%

void main(int argc,char *argv[])
{
    if(argc!=3)
    {
        printf("usage: %s <src file> <dst file>\n",argv[0]);
        return;
    }
    yyin=fopen(argv[1],"r");
    yyout=fopen(argv[2],"w");
    yylex();
}