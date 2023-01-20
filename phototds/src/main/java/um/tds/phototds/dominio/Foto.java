package um.tds.phototds.dominio;

import um.tds.phototds.controlador.Controlador;

public class Foto extends Publicacion {
	//Atributos
	private String path;

	//Constructor DAO TODO comprobar si pasa un usuario o un string el señor dao
	public Foto(String user, String titulo, String fecha, String descripcion, 
			String meGustas, String hashtags, String comentarios, String path) {
		super(user,titulo, fecha, descripcion, 
				meGustas, hashtags, comentarios);
		this.path = path;
	}

	
	//Constructor básico
	public Foto(Usuario user, String titulo, String descripcion, String path) {
		super(user, titulo, descripcion);
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
