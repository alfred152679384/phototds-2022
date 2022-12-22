package um.tds.phototds.controlador;

import java.time.LocalDate;

import um.tds.phototds.dao.*;
import um.tds.phototds.dominio.*;

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
	public Usuario getUsuarioActual() {
		return this.usuario;
	}

	// Funcionalidad
	public boolean loginUser(String username, String p) {
		assert (estado == Status.PRE_LOGIN);
		Usuario aux = RepoUsuarios.INSTANCE.findUsuario(username);
		if (aux != null && aux.getCont().equals(p)) {
			this.usuario = aux;
			estado = Status.LOGED;
			return true;
		}
		return false;

	}

	public boolean registerUser(String username, String nombre, String email, String cont, String fechN, String foto,
			String presentacion) {// Hay que ampliar los datos con los campos que se piden
		assert (estado == Status.PRE_LOGIN);
		if (RepoUsuarios.INSTANCE.findUsuario(username) != null) {
			return false;
		}
		Usuario u = new Usuario(username, nombre, email, cont, fechN, foto, presentacion);
		UsuarioDAO daoUser = factoria.getUsuarioDAO();
		daoUser.create(u);
		RepoUsuarios.INSTANCE.addUsuario(u);
		estado = Status.LOGED;
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
	
	public Object[] getFotos() {
		return RepoPublicaciones.INSTANCE.getFotos();	
	}
}
