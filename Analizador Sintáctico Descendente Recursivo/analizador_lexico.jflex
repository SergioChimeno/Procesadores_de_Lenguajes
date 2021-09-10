// Code, imports and packages

import java.io.*;

%%
// Options and Declarations
/* Notes:
 *	1. File is not of type 'standalone' since it is gona feed a parser 
 *	2. yylex() should return a Yytoken object with information of the current token: %type Yytoken
 */

%class Lexer 
%type Yytoken
%line
%column


DIGIT=[0-9]
INTEGER={DIGIT}+
ENDOFLINE=\n|\r|\n\r
BLANK={ENDOFLINE}+|[ \t\f]+

%{
int counter_semicolon=0;
int counter_leftbrackets=0;
int counter_rightbrackets=0;
int counter_op=0;
int counter_int=0;
String lexeme=null;
%}

%%
// Rules and Actions

";" {
	lexeme=yytext();
	System.out.println("[Lex] (SEMICOLON)"); 
	return new Yytoken(++counter_semicolon,lexeme,"SEMICOLON",(yyline+1),(yycolumn+1)); }
	
"(" {
	lexeme=yytext();
	System.out.println("[Lex] (LEFT_BRACKET)"); 
	return new Yytoken(++counter_leftbrackets,lexeme,"LEFT_BRACKET",(yyline+1),(yycolumn+1));}
	
")" {
	lexeme=yytext();
	System.out.println("[Lex] (RIGHT_BRACKET)"); 
	return new Yytoken(++counter_rightbrackets,lexeme,"RIGHT_BRACKET",(yyline+1),(yycolumn+1));}
	
"+" {
	lexeme=yytext();
	System.out.println("[Lex] (ADD, \""+lexeme+"\")"); 
	return new Yytoken(++counter_op,lexeme,"ADD",(yyline+1),(yycolumn+1));}

"-" {
	lexeme=yytext();
	System.out.println("[Lex] (SUB, \""+lexeme+"\")"); 
	return new Yytoken(++counter_op,lexeme,"SUB",(yyline+1),(yycolumn+1));}

"*" {
	lexeme=yytext();
	System.out.println("[Lex] (TIMES, \""+lexeme+"\")"); 
	return new Yytoken(++counter_op,lexeme,"TIMES",(yyline+1),(yycolumn+1));}

"/" {
	lexeme=yytext();
	System.out.println("[Lex] (DIV, \""+lexeme+"\")"); 
	return new Yytoken(++counter_op,lexeme,"DIV",(yyline+1),(yycolumn+1));}


{INTEGER} {
			lexeme=yytext();
			System.out.println("[Lex] (INTEGER, "+lexeme+")");
			return new Yytoken(++counter_int,lexeme,"INTEGER",(yyline+1),(yycolumn+1));
		  }
{BLANK} {}
. { lexeme=yytext();
	System.out.println("[Lex] Lexical Error: <" + lexeme + "> found in line "+ (yyline+1) + ", column "+ (yycolumn+1)); 	  
  	System.out.println("[Lex] Recovering from error, skipping lexeme <"+yytext()+">");
  }
  
  



