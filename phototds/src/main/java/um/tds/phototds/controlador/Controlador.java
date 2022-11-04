package um.tds.phototds.controlador;

import um.tds.phototds.dao.*;
import um.tds.phototds.dominio.*;

public enum Controlador {
	//Atributos
	INSTANCE;//Singleton
	private Usuario usuario;
	private FactoriaDAO factoria;
	
	//Contructor privado (singleton)
	private Controlador() {
		this.usuario = null;
		try {
			factoria = FactoriaDAO.getInstancia();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	//Getters & Setters
	public Usuario getUsuarioActual() {
		return this.usuario;
	}
	
	
	//Funcionalidad
	public boolean loginUser(String username, String p) {
		Usuario aux = RepoUsuarios.INSTANCE.findUsuario(username);
		if(aux != null && aux.getCont().equals(p)) {
			this.usuario = aux;
			System.out.println(aux.toString());
			return true;
		}
		return false;
		
	}

	public boolean registerUser(String username, String nombre, String email, String cont, String fechN, String foto, String presentacion) {//Hay que ampliar los datos con los campos que se piden
		if(RepoUsuarios.INSTANCE.findUsuario(username) != null) {
			return false;
		}
		Usuario u = new Usuario(username, nombre, email, cont, fechN, foto, presentacion); 
		UsuarioDAO daoUser = factoria.getUsuarioDAO();
		daoUser.create(u);
		u.toString();		
		RepoUsuarios.INSTANCE.addUsuario(u);
		return true;
	}
	
	public boolean deleteUser(Usuario username) {
		if(RepoUsuarios.INSTANCE.findUsuario(username.getUsername()) == null) {
			return false;
		}
		UsuarioDAO daoUser = factoria.getUsuarioDAO();
		daoUser.delete(username);
		return true;
	}

}
