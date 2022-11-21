package um.tds.phototds.dominio;

public class Comentario {
	//Atributos
	private Usuario autor;
	private String texto;

	//Constructor
	public Comentario(Usuario autor, String texto) {
		super();
		this.autor = autor;
		this.texto = texto;
	}

	//getters-setters
	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	//Funcionalidad
	
}
