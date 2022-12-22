package um.tds.phototds.dominio;

public class Foto extends Publicacion {
	//Atributos
	private String path;

	//Constructor DAO
	public Foto(String titulo, String fecha, String descripcion, String meGustas, String comentarios, String path) {
		super(titulo, fecha, descripcion, meGustas, comentarios);
		this.path = path;
	}

	
	//Constructor básico
	public Foto(String titulo, String descripcion, String path) {
		super(titulo, descripcion, new String[0]);//TODO: Hay que cambiar este array vacio por los hashtag, pero todavía no
		this.path = path;
	}
	

	//getters-setters
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	//Funcionalidad
	
}
