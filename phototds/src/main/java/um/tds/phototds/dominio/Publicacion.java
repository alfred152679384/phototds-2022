package um.tds.phototds.dominio;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import um.tds.phototds.controlador.Controlador;

public class Publicacion implements Comparable<Publicacion> {
	// Constantes
	private static final char SPACE = ' ';
	private static final char AlMOHADILLA = '#';
	private static final String LISTA_VACIA = "[]";
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
	public static final DateTimeFormatter HUMAN_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	// Atributos
	private int id;
	private String titulo;
	private Usuario user;
	private LocalDateTime fecha;
	private String descripcion;
	private int meGustas;
	private LinkedList<String> hashtags;// TODO
	private LinkedList<Comentario> comentarios;

	// Constructor básico
	public Publicacion(Usuario user, String titulo, String descripcion) {
		this.user = user;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fecha = LocalDateTime.now();
		this.hashtags = new LinkedList<String>(detectarHashtags(descripcion));
		this.comentarios = new LinkedList<Comentario>();
	}

	// Constructor DAO
	public Publicacion(String user, String titulo, String fecha, String descripcion, 
			String meGustas, String hashtags, String comentarios) {
		this.user = Controlador.INSTANCE.findUsuario(user).get();
		this.titulo = titulo;
		this.fecha = LocalDateTime.parse(fecha, FORMATTER);
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

	public String getFechaDAO() {
		return fecha.format(FORMATTER);
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

	public String getMegustasDAO() {
		return Integer.toString(this.meGustas);
	}
	
	public String getHashtagsDAO() {
		return hashtagToStringDAO();
	}
	
	public List<String> getHashtags(){
		return Collections.unmodifiableList(this.hashtags);
	}

	public List<Comentario> getComentarios() {
		return Collections.unmodifiableList(this.comentarios);
	}

	public String getComentariosDAO() {
		return Comentario.comentariosToString(this.comentarios);
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
	public boolean addComentario(Comentario c) {
		this.comentarios.add(c);
		return true;
	}
	
	public boolean darMeGusta() {
		this.meGustas++;
		return true;
	}
	
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
		String[] l = aux.split(",");
		for (int i = 0; i < l.length; i++) {
			list.add(l[i]);
		}
		return list;
	}
	
	public boolean equals(Object obj) {
		return ((Publicacion) obj).getId() == getId();
	}
	
	public int compareTo (Publicacion p) {
//		return getFecha().compareTo(p.getFecha());
		return p.getFecha().compareTo(getFecha());
	}

}
