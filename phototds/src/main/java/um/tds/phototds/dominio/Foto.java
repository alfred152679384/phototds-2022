package um.tds.phototds.dominio;

import java.time.LocalDateTime;
import java.util.List;

import um.tds.phototds.controlador.Controlador;

public class Foto extends Publicacion {
	//Atributos
	private String path;

	//Constructor básico
	public Foto(Usuario user, String titulo, String descripcion, String path) {
		super(user, titulo, descripcion);
		this.path = path;
	}
	
	//Constructor DAO 
	public Foto(String userDAO, String titulo, LocalDateTime fecha, String descripcion, 
			int meGustas, List<String> hashtags, String comentariosDAO, String path) {
		super(userDAO ,titulo, fecha, descripcion, meGustas, hashtags, comentariosDAO);
		this.path = path;
	}

	//getters-setters
	public String getPath() {
		return path;
	}
	
	//Funcionalidad
	@Override
	public void darMeGusta() {
		this.meGustas++;
	}
}
