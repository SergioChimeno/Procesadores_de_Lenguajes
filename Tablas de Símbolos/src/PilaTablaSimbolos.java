/*
  Autor: Sergio Chimeno Alegre
  Practica: Practica Trablas de Simbolos
 */
import java.util.Iterator;
import java.util.LinkedList;

public class PilaTablaSimbolos extends LinkedList<TablaSimbolos>{

	/**
	 * Devuelve un string que representa la pila
	 * 	Imprime primero el elemento en la cima, y sigue hasta la base
	 */
	@Override
	public String toString() {
		//String
		StringBuilder str=new StringBuilder("[");
		
		Iterator<TablaSimbolos> it=this.descendingIterator();
		if(it.hasNext()) str.append("\n");
		while(it.hasNext())
			str.append(it.next().toString()+"\n");
		str.append("]");
		
		return str.toString();
	}
}
