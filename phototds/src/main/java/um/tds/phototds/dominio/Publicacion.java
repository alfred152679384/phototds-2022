package um.tds.phototds.dominio;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import um.tds.phototds.controlador.Controlador;

public class Publicacion {
	// Constantes
	private static final char SPACE = ' ';
	private static final char AlMOHADILLA = '#';
	private static final String LISTA_VACIA = "[]";

	// Atributos
	private int id;
	private String titulo;
	private Usuario user;
	private String fecha;
	private String descripcion;
	private int meGustas;
	private LinkedList<String> hashtags;// TODO
	private LinkedList<Comentario> comentarios;

	// Constructor básico
	public Publicacion(Usuario user, String titulo, String descripcion) {
		this.user = user;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fecha = LocalDate.now().toString();
		this.hashtags = new LinkedList<String>(detectarHashtags(descripcion));
		this.comentarios = new LinkedList<Comentario>();
	}

	// Constructor DAO
	public Publicacion(String user, String titulo, String fecha, String descripcion, 
			String meGustas, String hashtags, String comentarios) {
		this.user = Controlador.INSTANCE.findUsuario(user).get();
		this.titulo = titulo;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.meGustas = Integer.parseInt(meGustas);
		this.hashtags = new LinkedList<>(hashtagToList(hashtags));
		this.comentarios = new LinkedList<>(Comentario.comentariosToList(comentarios));
	}

	// getters-setters
	public Usuario getUsuario() {
		return this.user;
	}
	
	public String getUsuarioDAO() {
		return this.user.getUsername();
	}
	
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
	
	public String getHashtagsDAO() {
		return hashtagToStringDAO();
	}

	public List<Comentario> getComentarios() {
		return Collections.unmodifiableList(this.comentarios);
	}

	public String getComentariosDAO() {
		return Comentario.comentariosToString(this.comentarios);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// Funcionalidad
	protected List<String> detectarHashtags(String s) {
		List<String> list = new LinkedList<>();
		String aux = s;
		String hashtag;
		int index;
		while ((index = aux.indexOf(AlMOHADILLA)) != -1) {
			aux = aux.substring(index);
			hashtag = aux.substring(index, aux.indexOf(SPACE));// Acotamos el hashtag
			list.add(hashtag);
			aux = aux.substring(aux.indexOf(SPACE));// Actualizamos aux
		}
		return list;
	}

	protected String hashtagToStringDAO() {
		String l = "[";
		for (int i = 0; i < this.hashtags.size(); i++) {
			if (i == 0)
				l += this.hashtags.get(i);
			else
				l += "," + this.hashtags.get(i);
		}
		l += "]";
		return l;
	}

	protected List<String> hashtagToList(String s) {
		if (s.equals(LISTA_VACIA))
			return Collections.emptyList();
		List<String> list = new LinkedList<>();
		String aux = s.substring(1, s.length() - 1);
		String[] l = s.split(",");
		for (int i = 0; i < l.length; i++) {
			list.add(l[i]);
		}
		return list;
	}

}
