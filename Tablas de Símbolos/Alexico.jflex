import java_cup.runtime.*;

%%
%unicode
%cup
%line
%column
%ignorecase

%{

	private Symbol get_symbol(int tipo, Object valor){
		return new Symbol(tipo,yyline,yycolumn,valor);
	}
	
	private Symbol get_symbol(int tipo){
		return new Symbol(tipo,yyline,yycolumn);
	}

%}


%%

"int"   { return get_symbol(sym.TIPO_INT); }
"float" { return get_symbol(sym.TIPO_FLOAT); }
"+" { return get_symbol(sym.PLUS); }
"-" { return get_symbol(sym.MINUS); }
"*" { return get_symbol(sym.TIMES); }
"/" { return get_symbol(sym.DIV); }
"^" { return get_symbol(sym.POW); }
"sqrt"  { return get_symbol(sym.SQRT); }
"print" { return get_symbol(sym.PRINT); }
"(" { return get_symbol(sym.LEFT_BRACKET); }
")" { return get_symbol(sym.RIGHT_BRACKET); }
"[" { return get_symbol(sym.LEFT_SQBRACKET); }
"]" { return get_symbol(sym.RIGHT_SQBRACKET); }
"{" { return get_symbol(sym.LEFT_BRACE); }
"}" { return get_symbol(sym.RIGHT_BRACE); }
"=" { return get_symbol(sym.ASSIGN); }
";" { return get_symbol(sym.SEMICOLON); }
[0-9]+ { return get_symbol(sym.INTEGER, new Integer(yytext())); }
[0-9]+(\.[0-9]+)?(e+[+-]?[0-9]+)? { return get_symbol(sym.FLOAT, new Float(yytext())); }
[:jletter:][:jletterdigit:]* { return get_symbol(sym.ID, yytext()); }

[ \t\r\n\f]+ {}

. { System.out.println("[Lex] Error léxico en línea "+yyline+", columna "+yycolumn); }

