package um.tds.phototds.dominio;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Publicacion implements Comparable<Publicacion> {
	// Constantes
	private static final char SPACE = ' ';
	private static final char AlMOHADILLA = '#';
	public static final DateTimeFormatter HUMAN_FORMATTER = 
			DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	// Atributos
	private int id;
	private String titulo;
	private Usuario user;
	private Optional<String> usuarioDAO;
	private LocalDateTime fecha;
	private String descripcion;
	protected int meGustas;
	private List<String> hashtags;
	private List<Comentario> comentarios;
	private Optional<String> comentariosDAO;

	// Constructor básico
	public Publicacion(Usuario user, String titulo, String descripcion) {
		this.user = user;
		this.usuarioDAO = Optional.empty();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fecha = LocalDateTime.now();
		this.hashtags = new LinkedList<String>(detectarHashtags(descripcion));
		this.comentarios = new LinkedList<>();
		this.comentariosDAO = Optional.empty();
	}

	// Constructor DAO
	public Publicacion(String userDAO, String titulo, LocalDateTime fecha, String descripcion, 
			int meGustas, List<String> hashtags, String comentariosDAO) {
//		this.user = Not initialized;
		this.usuarioDAO = Optional.of(userDAO);
		this.titulo = titulo;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.meGustas = meGustas;
		this.hashtags = hashtags;
		this.comentarios = new LinkedList<>();
		this.comentariosDAO = Optional.of(comentariosDAO);
	}

	// getters-setters
	public Usuario getUsuario() {
		return this.user;
	}
	
	public void setUsuario(List<Usuario> users) {
		if(usuarioDAO.isEmpty())
			return;
		Optional<Usuario> user = users.stream()
			.filter(u -> u.getUsername().equals(usuarioDAO.get()))
			.findFirst();
		if(user.isEmpty())
			return;
		usuarioDAO = Optional.empty();
		this.user = user.get();
	}
	
	public String getTitulo() {
		return titulo;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getMeGustas() {
		return meGustas;
	}
	
	public List<String> getHashtags(){
		return this.hashtags;
	}
	
	public List<Comentario> getComentarios() {
		return this.comentarios;
	}
	
	public void setComentarios(List<Usuario> users) {
		if(comentariosDAO.isEmpty())
			return;
		this.comentarios = new LinkedList<>(
				Comentario.comentariosToList(users, this.comentariosDAO.get()));
		this.comentariosDAO = Optional.empty();
	}
	
	public String getHashtagContaining(String hashtag) {
		List<String> h = hashtags.stream()
			.filter(s -> s.contains(hashtag))
			.collect(Collectors.toList());
		return h.get(0);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// Funcionalidad
	public void addHashtag(String h) {
		this.hashtags.add(AlMOHADILLA+h);
	}
	
	public boolean addComentario(Usuario autor, String coment) {
		this.comentarios.add(new Comentario(autor, coment));
		return true;
	}
	
	//Cada hijo da me gusta de una manera distinta
	public abstract void darMeGusta();
	
	protected List<String> detectarHashtags(String s) {
		List<String> list = new LinkedList<>();
		String aux = s;
		String hashtag;
		int i, f;
		while ((i = aux.indexOf(AlMOHADILLA)) != -1) {
			aux = aux.substring(i);
			i = 0;
			f = aux.indexOf(SPACE);
			if (f == -1)
				f = aux.length();
			hashtag = aux.substring(i, f);
			list.add(hashtag);
			aux = aux.substring(f);
		}
		return list;
	}
	
	public boolean equals(Object obj) {
		return ((Publicacion) obj).getId() == getId();
	}
	
	public int compareTo (Publicacion p) {
		return p.getFecha().compareTo(this.getFecha());
	}
}
