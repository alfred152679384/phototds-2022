package um.tds.phototds.controlador;

import java.awt.Frame;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import um.tds.phototds.dao.*;
import um.tds.phototds.dominio.*;
import um.tds.phototds.interfaz.PrincipalGUI;

enum Status {
	PRE_LOGIN, LOGED
}

public enum Controlador {
	INSTANCE;// Singleton

	// Atributos
	private Usuario usuario;
	private FactoriaDAO factoria;
	private Status estado;

	// Contructor privado (singleton)
	private Controlador() {
		estado = Status.PRE_LOGIN;
		this.usuario = null;

		try {
			factoria = FactoriaDAO.getInstancia();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	// Getters & Setters
	public String getUsuarioActual() {
		return this.usuario.getUsername();
	}
	
	public String getCorreoUsuario() {
		return this.usuario.getEmail();
	}
	
	public List<Foto> getFotosPrincipal() {
		return RepoPublicaciones.INSTANCE.getFotosPrincipal();	
	}
	
	public List<Foto> getFotosPerfil(){
		//TODO return RepoPublicaciones.INSTANCE.getFotosPerfil(this.usuario.getUsername());
		return RepoPublicaciones.INSTANCE.getFotosPrincipal();
	}

	public int getNumPublicaciones() {
		return RepoPublicaciones.INSTANCE.getNumPublicaciones(this.usuario.getUsername());
	}
	
	public int getNumSeguidores() {
		return usuario.getNumSeguidores();  
	}
	
	public int getNumSeguidos() {
		return usuario.getNumSeguidos();
	}
	
	public String getNombreUsuario() {
		return usuario.getNombre();
	}

	// Funcionalidad
	public boolean loginUser(String username, String p) {
		if(estado != Status.PRE_LOGIN) {
			return false;
		}
		Optional<Usuario> aux = RepoUsuarios.INSTANCE.findUsuario(username);
		if (aux.isPresent() && aux.get().getPassword().equals(p)) {
			this.usuario = aux.get();
			estado = Status.LOGED;
			return true;
		}
		return false;

	}

	public boolean registerUser(String username, String nombre, String email, String cont, 
			String fechN, Optional<String> fotoPerfil, Optional<String> presentacion) {
		Optional<Usuario> optUser = RepoUsuarios.INSTANCE.findUsuario(username);
		if (estado != Status.PRE_LOGIN || optUser.isPresent()) {
			return false;
		}
		//Creamos el usuario en el dominio
		Usuario u = new Usuario(username, nombre, email, cont, fechN, fotoPerfil, presentacion);
		
		//Añadimos al usuario al servidor de Persistencia
		UsuarioDAO daoUser = factoria.getUsuarioDAO();
		daoUser.create(u);
		RepoUsuarios.INSTANCE.addUsuario(u);
		return true;
	}

	public boolean deleteUser(Usuario username) {
		if (RepoUsuarios.INSTANCE.findUsuario(username.getUsername()) == null) {
			return false;
		}
		UsuarioDAO daoUser = factoria.getUsuarioDAO();
		daoUser.delete(username);
		return true;
	}

	public boolean addFoto(String path, String title, String desc) {
		Publicacion p = new Foto(title, desc, path);
		PublicacionDAO daoFoto = factoria.getPublicacionDAO();
		daoFoto.create(p);
		RepoPublicaciones.INSTANCE.addPublicacion(p);
		
		return true;
	}
	
	public Optional<Usuario> findUsuario(String username) {
		return RepoUsuarios.INSTANCE.findUsuario(username);
	}
	
}
