/*
  Autor: Sergio Chimeno Alegre
  Practica: Practica Trablas de Simbolos
 */
public class Simbolo {

	private String nombre;
	private String tipo;
	private Number valor;
	
	public Simbolo(String nombre, String tipo) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		if(tipo.equals("float"))
			valor = new Float(0);
		else if(tipo.equals("int"))
			valor = new Integer(0);
		else //Si es un array los inicializamos a null, de esta forma las expresiones no tendran resultado
			valor=null;
	}
	public Simbolo(String nombre, String tipo, Number valor) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.valor = valor;
	}
	
	//Setters y getters
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Number getValor() {
		return valor;
	}
	public void setValor(Number valor) {
		this.valor = valor;
	}
	
	//Operaciones
	public Simbolo cambiarSigno() {
		Number valor;
		if(this.tipo.equals("int")) 
			valor=-(Integer)this.valor;
		else
			valor=-(Float)this.valor;
		
		return new Simbolo(nombre,tipo,valor);
	}
	public Simbolo sqrt() {
		return new Simbolo("","float",new Float(Math.sqrt(valor.floatValue())));
	}
	public Simbolo mas(Simbolo s) {
		if(tipo.equals("float") || s.tipo.contentEquals("float")) 
			return new Simbolo("","float",valor.floatValue()+s.valor.floatValue());
		else 
			return new Simbolo("","int",valor.intValue()+s.valor.intValue());
	}
	public Simbolo menos(Simbolo s) {
		if(tipo.equals("float") || s.tipo.contentEquals("float")) 
			return new Simbolo("","float",valor.floatValue()-s.valor.floatValue());
		else 
			return new Simbolo("","int",valor.intValue()-s.valor.intValue());
	}
	public Simbolo por(Simbolo s) {
		if(tipo.equals("float") || s.tipo.contentEquals("float")) 
			return new Simbolo("","float",valor.floatValue()*s.valor.floatValue());
		else 
			return new Simbolo("","int",valor.intValue()*s.valor.intValue());
	}
	public Simbolo entre(Simbolo s) {
		if(tipo.equals("float") || s.tipo.contentEquals("float")) 
			return new Simbolo("","float",valor.floatValue()/s.valor.floatValue());
		else 
			return new Simbolo("","int",valor.intValue()/s.valor.intValue());
	}
	public Simbolo elevado_a(Simbolo s) {
		if(tipo.equals("float") || s.tipo.contentEquals("float")) 
			return new Simbolo("","float",new Float(Math.pow(valor.floatValue(), s.valor.floatValue())));
		else 
			return new Simbolo("","int",new Integer((int)(Math.pow(valor.intValue(), s.valor.intValue()))));
	}
		
	
	@Override
	public String toString() {
		return "["+nombre +"; "+tipo+"; " + valor + "]";
	}
	
}
