package um.tds.phototds.dominio;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Publicacion {
	//Atributos
	private int id;
	private String titulo;
	private String fecha;
	private String descripcion;
	private int meGustas;
	private LinkedList<String> hashtags;
	private LinkedList<Comentario> comentarios; 
	
	// Constructor básico
	public Publicacion(String titulo, String descripcion, String ... hashtags) {
		super();
		this.titulo = titulo;
		this.fecha = LocalDate.now().toString();
		this.descripcion = descripcion;
		for(int i=0; i< hashtags.length; i++) {
			this.hashtags.add(hashtags[i]);
		}
	
	}
	
	//Constructor DAO
	public Publicacion(String titulo, String fecha, String descripcion, String meGustas, String hashtags, String comentarios) {
		this(titulo, fecha, descripcion);
		this.meGustas = Integer.parseInt(meGustas);
		//Cargar hashtags
		
		//Cargar comentarios
	}

	//getters-setters
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getMeGustas() {
		return meGustas;
	}
	
	public String getMegustas() {
		return Integer.toString(this.meGustas);
	}

	public void setMeGustas(int meGustas) {
		this.meGustas = meGustas;
	}

	public List<Comentario> getComentarios(){
		return Collections.unmodifiableList(this.comentarios);
	}
	
	public String getComentariosString() {
		return this.comentarios.toString();
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	//Funcionalidad
	//-> Por implementar si es que es necesaria
	
}
