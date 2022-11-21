package um.tds.phototds.dominio;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Usuario {
	//Constantes
	
	//Atributos
	private int id;
	private String username;
	private String nombre;
	private String email;
	private String cont;
	private String fechN;
	private String foto;
	private String presentación;
	private boolean isPremium;//Añadir a Persistencia
	private LinkedList<Publicacion> publicaciones;
	private LinkedList<Usuario> seguidores;
	private LinkedList<Usuario> seguidos;

	
	//Constructor básico para nuevo usuario
	public Usuario(String username, String nombre, String email, String cont, String fechN, String foto, String presentación) {
		super();
		this.username = username;
		this.nombre = nombre;
		this.email = email;
		this.cont = cont;
		this.fechN = fechN;
		this.foto = foto;
		this.presentación = presentación;
		this.isPremium = false;
		this.publicaciones = new LinkedList<Publicacion>();
		this.seguidores = new LinkedList<Usuario>();
		this.seguidos = new LinkedList<Usuario>();
	}
	
	
	//Constructor DAO
	public Usuario(String username, String nombre, String email, String cont, String fechN, String foto, String presentacion,
			String isPremium, String publicaciones, String seguidores, String seguidos) {
		this(username, nombre, email, cont, fechN, foto, presentacion);
		if(isPremium.equals("true"))
			this.isPremium = true;
		else this.isPremium = false;
		//Crear cargador xml de las fotos (creo que va aquí) (aunque también habrá otro en repo)
	}

	//getters-setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}

	public void setUsuario(String usuario) {
		this.username = usuario;
	}

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	public String getFechN() {
		return fechN;
	}

	public void setFechN(String fechN) {
		this.fechN = fechN;
	}
	
	public String getFoto() {
		return foto;
	}
	
	public void setFoto(String newFoto) {
		this.foto = newFoto;
	}

	public String getPresentación() {
		return presentación;
	}

	public void setPresentación(String presentación) {
		this.presentación = presentación;
	}


	public boolean isPremium() {
		return isPremium;
	}
	
	public String isPremiumString() {
		if(this.isPremium)
			return "true";
		return "false";
	}
	
	public void setPremium(boolean premium) {
		this.isPremium = premium;
	}


	public List<Publicacion> getListaPubli() {
		return Collections.unmodifiableList(this.publicaciones);
	}
	
	public String getPubliString() {
		return this.publicaciones.toString();
	}


	public List<Usuario> getSeguidores() {
		return Collections.unmodifiableList(this.seguidores);
	}
	
	public String getSeguidoresString() {
		return this.seguidores.toString();
	}

	
	public List<Usuario> getSeguidos() {
		return Collections.unmodifiableList(this.seguidos);
	}
	
	public String getSeguidosString() {
		return this.seguidos.toString();
	}

	//Funcionalidad

//	@Override
//	public String toString() {
//		return "Usuario [id=" + id + ", username=" + username + ", nombre=" + nombre + ", email=" + email + ", cont="
//				+ cont + ", fechN=" + fechN + ", foto=" + foto + ", presentación=" + presentación + ", isPremium="
//				+ isPremium + ", publicaciones=" + publicaciones + ", seguidores=" + seguidores + ", seguidos="
//				+ seguidos + "]";
//	}
	


	
	
	
	
}
