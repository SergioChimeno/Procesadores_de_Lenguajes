import java.io.*;

%%

%class AnalizadorLexico
%public
%standalone
%line
%column
%state ANALIZADOR_JAVA ANALIZADOR_PHP STRING_JAVA STRING_PHP

%{
	private void println(String s){
		System.out.println(s);
	}
	private void printlnErr(String msj){
		//Funcion que imprime el error en linea y columna
		System.out.println("line:"+yyline+" column:"+yycolumn+", "+msj);
	}
%}

%eof{
	if(yystate()==ANALIZADOR_JAVA)
		printlnErr("ERROR JAVA(Sintaxis inválida): No has cerrado el bloque JAVA");
	else if(yystate()==ANALIZADOR_PHP)
		printlnErr("ERROR PHP(Sintaxis inválida): No has cerrado el bloque PHP");
	else if(yystate()==STRING_JAVA)
		printlnErr("ERROR JAVA(Lexema inválido): No has cerrado el último String");	
	else if(yystate()==STRING_PHP)
		printlnErr("ERROR PHP(Lexema inválido): No has cerrado el último String");	
%eof}

espacio=[\t\n\s]+
num_mes=[1-9]|1[012]
letras=[a-zA-Z]
digitos=[0-9]
ops=\=\=|>
id=[{letras}_$][{letras}_${digitos}]*

//JAVA
res_java = if|else 

//PHP
res_php = if|else|elseif
 
%%
//Cunado se encuentra un error, solo se imprime y se sigue la ejecucion

<YYINITIAL>{
	BeginJava   {yybegin(ANALIZADOR_JAVA);println("JAVA_INI");}
	BeginPHP    {yybegin(ANALIZADOR_PHP);println("PHP_INI");}
	.*		    {printlnErr("ERROR(Lexema inválido): No has iniciado el bloque JAVA o PHP");}
}

<ANALIZADOR_JAVA>{
	\{          {println("JAVA_ABRE_LLAVE");}
	\}          {println("JAVA_CIERRA_LLAVE");}
	{ops}       {println("JAVA_OPERADOR("+yytext()+")");}
	{res_java}  {println("JAVA_RESERVADAS("+yytext()+")");}
	=           {println("JAVA_ASIGN");}
	\"		    {println("JAVA_CAD_INI");yybegin(STRING_JAVA);}
	\(          {println("JAVA_ABRE_PAR");}
	\)		    {println("JAVA_CIERRA_PAR");}
	\.          {println("JAVA_PUNTO");}
	;           {println("JAVA_FIN_INS");}
	EndJava     {yybegin(YYINITIAL);println("JAVA_FIN");}
	{id}        {println("JAVA_ID("+yytext()+")");}
	{espacio}   {}
	.           {printlnErr("ERROR JAVA(Lexema inválido): "+yytext());}
}

<STRING_JAVA>{
	\n              {printlnErr("ERROR JAVA(Lexema inválido): Un String de Java no puede ocupar varias lineas");}
	([^\n\"]|\\\")* {println("JAVA_STRING("+yytext()+")");}
	\"		        {println("JAVA_CAD_FIN");yybegin(ANALIZADOR_JAVA);}
}

<ANALIZADOR_PHP>{
	\{          {println("PHP_ABRE_LLAVE");}
	\}          {println("PHP_CIERRA_LLAVE");}
	{ops}       {println("PHP_OPERADOR("+yytext()+")");}
	{res_php}   {println("PHP_RESERVADAS("+yytext()+")");}
	=           {println("PHP_ASIGN");}
	\"		    {println("PHP_CAD_INI");yybegin(STRING_PHP);}
	\(          {println("PHP_ABRE_PAR");}
	\)		    {println("PHP_CIERRA_PAR");}
	\.          {println("PHP_PUNTO");}
	;           {println("PHP_FIN_INS");}
	EndPHP      {yybegin(YYINITIAL);println("PHP_FIN");}
	{id}        {println("PHP_ID("+(yytext().charAt(0)=='$'?yytext().substring(1):yytext())+")");}
	{espacio}   {}
	.		    {printlnErr("ERROR PHP(Lexema inválido): "+yytext());}
}

<STRING_PHP>{
	([^\"]|\\\")* {println("PHP_STRING("+yytext()+")");}
	\"		      {println("PHP_CAD_FIN");yybegin(ANALIZADOR_PHP);}
}

{espacio}       {}