package um.tds.phototds.dominio;

import java.time.LocalDateTime;

public class Notificacion {
	//Atributos
	private Publicacion publicacion;
	private LocalDateTime fecha;
	
	//Constructor
	public Notificacion(Publicacion p, LocalDateTime d) {
		this.publicacion = p;
		this.fecha = d;
	}
	
	//Getters & Setters
	public Publicacion getPublicacion() {
		return this.publicacion;
	}
	
	public LocalDateTime getFechaPublicacion() {
		return this.fecha;
	}
}
