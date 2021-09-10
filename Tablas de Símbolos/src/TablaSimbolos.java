/*
  Autor: Sergio Chimeno Alegre
  Practica: Practica Trablas de Simbolos
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;


public class TablaSimbolos extends HashMap<String,Simbolo>{

	private PilaTablaSimbolos pila;
	
	public TablaSimbolos(PilaTablaSimbolos pila) {
		super();
		this.pila=pila;
	}
	
	public Simbolo get(String clave) {
		if(this.containsKey(clave))
			return super.get(clave);
		else {
			TablaSimbolos ts = null;
			Iterator<TablaSimbolos> it=pila.descendingIterator();
			while(it.hasNext()) {
				ts=it.next();
				if(ts.containsKey(clave))
					return ts.get(clave);
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder str=new StringBuilder("{");
		
		Iterator<Map.Entry<String,Simbolo>> it=this.entrySet().iterator();
		if(it.hasNext()) str.append("\n");
		while(it.hasNext()) {
			Entry<String, Simbolo> entrada = it.next();
			str.append("\t"+entrada.getKey()+":"+entrada.getValue()+"\n");
		}

		str.append("}");
		return str.toString();
	}
}
