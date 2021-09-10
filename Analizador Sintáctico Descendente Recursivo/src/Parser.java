/* Autor: Sergio Chimeno Alegre
 * Practica: Practica Analizador Sintáctico Descendente Recursivo
 */
import java.io.*;

public class Parser{
	// Properties: current token and lexical analyser that will check the token plus a counter of statements
	private Yytoken currentToken=null;
	private Yytoken prevToken=null;
	private static Lexer myLexer=null;  
	int stCounter=0; 
	
	// Constructor: set my_lexer value
	Parser (Lexer lex){
		myLexer=lex;
	}
	
	// Get type of current token
	private String getCurrentTkType(){
		return currentToken.m_tktype;
	}
	
	// Get next token
	private void getNextToken() {
		prevToken=currentToken;
		try {
			currentToken=myLexer.yylex(); 
			if (currentToken==null){ //Found end of file
				System.out.println("\t[Parser] End of file");
				currentToken= new Yytoken(0,"End of File","EOF",prevToken.m_line,prevToken.m_column);
			}	
		}
		catch (IOException e){
			currentToken.error("Error getting token next to ");
		}
	}
	
	// BEGINNING OF RECURSIVE-DESCENDENT PARSING
	
	//Inicio Producciones
	/*
	S-->T;
	T-->FT'
	T'-->opFT'| epsilon
	F-->(T)|num
	
	S -->E;
	E -->TE'
	E'-->+TE'|-TE'|epsilon
	T -->FT'
	T'-->*FT'|/FT'|epsilon
	F -->Int|(E)
	*/
	
	public void S() throws ErrorSentenciaInvalida { //S->E;
		stCounter++;
		System.out.println("\t=================================");
		System.out.println("\t[Parser] NEW STATEMENT [#"+stCounter+"]");
		E();
		match("SEMICOLON");  // ';': end of statement 	
	}
	
	public void E() throws ErrorSentenciaInvalida { //E->TE'
		T();
		Eprima();
	}
	
	public void T() throws ErrorSentenciaInvalida { //T->FT'
		F();
		Tprima();
	}
	
	public void F() throws ErrorSentenciaInvalida { //F->Int|(E)
		switch(getCurrentTkType()){
			case "LEFT_BRACKET":
				match("LEFT_BRACKET");
				E();
				match("RIGHT_BRACKET");
				break;
			case "INTEGER":
				match("INTEGER");
				break;
			default: //error
				match("INTEGER or LEFT_BRACKET"); //Con este string saltara el error e imprimirá el mensaje correcto
		}
	}

	public void Eprima() throws ErrorSentenciaInvalida { //E'->+TE'|-TE'|epsilon
		if(getCurrentTkType().equals("ADD")) {
			match("ADD");
			T();
			Eprima();
		}else if(getCurrentTkType().equals("SUB")) {
			match("SUB");
			T();
			Eprima();
		}
	}
	
	public void Tprima() throws ErrorSentenciaInvalida { //T'->*FT'|/FT'|epsilon
		if(getCurrentTkType().equals("TIMES")) {
			match("TIMES");
			F();
			Tprima();
		}else if(getCurrentTkType().equals("DIV")) {
			match("DIV");
			F();
			Tprima();
		}
	}
	
	// If current token matches the token type in the grammar, get next token. Otherwise, error
	public void match(String refTkType) throws ErrorSentenciaInvalida {
		if (getCurrentTkType().equals(refTkType)) {
			System.out.println("\t[Parser] Current token is correct ("+getCurrentTkType()+ ")");
			getNextToken();
		}
		else if (getCurrentTkType().equals("EOF")) {
			//Case of end of file
			if(! prevToken.m_tktype.contentEquals("SEMICOLON")) //Si la ultima sentencia no esta completa
				System.out.println("\t\t[Parser] Error: Se llego a final de archivo sin acabar la ultima sentencia ");
			
			throw new ErrorSentenciaInvalida();
		}
		else {
			currentToken.errorMatch(refTkType); // Current token does not match production in grammar
			
			// 1. Ignore following tokens until semicolon (i.e. end of statement) or EOF is found
			while ((getCurrentTkType().contentEquals("SEMICOLON")==false)&&(getCurrentTkType().contentEquals("EOF")==false)) {
					System.out.println("\t\t[Parser] Recovering from error: skipping "+getCurrentTkType());
					getNextToken();
			}
			
			//Ignoramos el semicolon porque pertenece a esta instruccion
			if((getCurrentTkType().contentEquals("SEMICOLON")==true)) {
				System.out.println("\t\t[Parser] Recovering from error: skipping "+getCurrentTkType());
				getNextToken();
			}
			
			//Tiramos lo que haya en la pila de llamadas, ya que no vamos a seguir analizando esta sentencia
			throw new ErrorSentenciaInvalida();
		}
	}
		
	
	public static void main(String[] argv) {
		  if (argv.length == 0) {
		      System.out.println("Syntax : java Parser  <inputfile)>");
		    }
		  else {
		 
		 try {
			java.io.FileInputStream stream = new java.io.FileInputStream(argv[0]);
		    java.io.Reader reader = new java.io.InputStreamReader(stream);
		    
			Lexer lex= new Lexer(reader);
			
			Parser recursive_descendent_parser=new Parser(lex);
			
			recursive_descendent_parser.getNextToken();
			do { 
				
				try {  recursive_descendent_parser.S();  } catch(ErrorSentenciaInvalida e) {}
			}
			while (recursive_descendent_parser.getCurrentTkType().equals("EOF")==false);
		}
		catch(IOException x) {
			System.out.println("\t[Parser] Error while reading "+x.toString()+(myLexer.yytext()));
		}
		  }
	}
	
}

 

