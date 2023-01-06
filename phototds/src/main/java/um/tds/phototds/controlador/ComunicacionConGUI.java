package um.tds.phototds.controlador;

import java.time.LocalDateTime;

public class ComunicacionConGUI {
	//Constantes
	public static final int MODE_USUARIOS = 0;
	public static final int MODE_HASHTAGS = 1;
	public static final int MODE_PROFILE_FOTO = 2;
	public static final int MODE_ENTRADA_PRINCIPAL = 3;
	public static final int MODE_ALBUM = 4;
	
	//Atributos
	private int mode;
	private String username;
	//-->Para la busqueda de usuarios
	private String pathFoto;
	private String nombreUsuario;
	//-->Para la busqueda de fotos
	private String hashtag;
	private int numSeguidoresUsuario;
	//-->Para entradas del panel principal
	private int idFoto;
	private LocalDateTime fecha;
	private int meGustas;
	private String titulo;
	private String desc;
	
	//Constructor para búsqueda Usuarios
	public ComunicacionConGUI (String pathFoto, String nombreUsuario, String username) {
		this.username = username;
		this.mode = MODE_USUARIOS;
		this.pathFoto = pathFoto;
		this.nombreUsuario = nombreUsuario;
	}
	
	//Constructor para búsqueda publicaciones
	public ComunicacionConGUI(String hashtag, int numSeguidoresUsuario, String username) {
		this.username = username;
		this.mode = MODE_HASHTAGS;
		this.hashtag = hashtag;
		this.numSeguidoresUsuario = numSeguidoresUsuario;
	}
	
	//Constructor para mostrar Fotos por el perfil
	public ComunicacionConGUI(int id, String pathFoto) {
		this.mode = MODE_PROFILE_FOTO;
		this.idFoto = id;
		this.pathFoto = pathFoto;
	}
	
	//Constructor para las entradas de fotos del panel principal
	public ComunicacionConGUI(int idFoto, String username, String path, LocalDateTime fecha, int meGustas, String titulo, String desc) {
		this.idFoto = idFoto;
		this.username = username; 
		this.pathFoto = path;
		this.fecha = fecha;
		this.meGustas = meGustas;
		this.titulo = titulo;
		this.desc = desc;
	}
	
	//Constructor para crear álbumes
	public ComunicacionConGUI(String pathFoto, String titulo, String descripcion, int mode) {
		this.pathFoto = pathFoto;
		this.titulo = titulo;
		this.desc = descripcion;
		this.mode = mode;
	}
	
	//Getters & Setters
	public int getMode() {
		return this.mode;
	}
	
	public String getPathFoto() {
		return this.pathFoto;
	}
	
	public String getNombreUsuario() {
		return this.nombreUsuario;
	}
	
	public String getHashtag() {
		return this.hashtag;
	}
	
	public int getSeguidoresUsuario() {
		return this.numSeguidoresUsuario;
	}
	
	public int getIdPublicacion() {
		return this.idFoto;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public LocalDateTime getFecha() {
		return this.fecha;
	}
	
	public int getMeGustas() {
		return this.meGustas;
	}
	
	public String getTitulo() {
		return this.titulo;
	}
	
	public String getDescripcion() {
		return this.desc;
	}
}
