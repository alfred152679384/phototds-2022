package um.tds.phototds.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Usuario {
	//Constantes
	private static final String SPACE = " ";
	private static final int TAM_FECHA = 3;
	
	//Atributos
	private int id;
	private String username;
	private String nombre;
	private String email;
	private String cont;
	private String fechN;
	private String foto;
	private String presentación;

	//Constructor
	public Usuario(String username, String nombre, String email, String cont, String fechN, String foto, String presentación) {
		super();
		this.username = username;
		this.nombre = nombre;
		this.email = email;
		this.cont = cont;
		this.fechN = fechN;
		this.foto = foto;
		this.presentación = presentación;//puede ser null;
		//faltan cosas?;
	}

	//Get-Set
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

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", nombre=" + nombre + ", email=" + email + ", cont="
				+ cont + ", fechN=" + fechN + ", foto=" + foto + ", presentación=" + presentación + "]";
	}
	
	
	
	
}
