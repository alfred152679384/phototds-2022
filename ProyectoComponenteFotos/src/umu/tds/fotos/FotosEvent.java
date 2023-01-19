package umu.tds.fotos;

import java.util.EventObject;

public class FotosEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	
	//Atributos
	private Fotos fotos;

	public FotosEvent(Object src, Fotos fotos) {
		super(src);
		this.fotos = fotos;
	}
	
	//Getters & Setters
	public Fotos getFotos(){
		return this.fotos;
	}
	
}
