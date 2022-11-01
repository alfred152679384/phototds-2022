package um.tds.phototds.clasesFuncionales;

import java.time.LocalDate;

public class Usuario {
	private int id;
	private String email;
	private String nombre;
	private String usuario;
	private String cont;
	private LocalDate fechN;
	//private Foto foto;
	private String presentación;

	//Constructor
	public Usuario(String email, String nombre, String usuario, String cont, LocalDate fechN, String presentación) {
		super();
		this.email = email;
		this.nombre = nombre;
		this.usuario = usuario;
		this.cont = cont;
		this.fechN = fechN;
		this.presentación = presentación;//puede ser null;
		//faltan cosas;
	}
	
	//Get-Set
	public String getPresentación() {
		return presentación;
	}
	public void setPresentación(String presentación) {
		this.presentación = presentación;
	}
	public String getEmail() {
		return email;
	}
	public String getNombre() {
		return nombre;
	}
	public String getUsuario() {
		return usuario;
	}
	public String getCont() {
		return cont;
	}
	public LocalDate getFechN() {
		return fechN;
	}
	
	
}
