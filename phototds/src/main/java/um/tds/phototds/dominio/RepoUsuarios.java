package um.tds.phototds.dominio;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import um.tds.phototds.dao.*;

public enum RepoUsuarios {
	//Atributos
	INSTANCE;//Singleton
	private FactoriaDAO factoria;
	private HashMap<Integer, Usuario> usuariosPorID;
	private HashMap<String, Usuario> usuariosPorLogin;
	
	//Contstructor privado (singleton)
	private RepoUsuarios(){
		usuariosPorID = new HashMap<Integer, Usuario>();
		usuariosPorLogin = new HashMap<String, Usuario>();
		
		try {
			factoria = FactoriaDAO.getInstancia();
			cargarUsuarios();
			
		} catch (DAOException eDAO) {
			   eDAO.printStackTrace();
		}
	}
	
	private void cargarUsuarios() {
		List<Usuario> listausuarios = factoria.getUsuarioDAO().getAll();
		for (Usuario usuario : listausuarios) {
			usuariosPorID.put(usuario.getId(), usuario);
			usuariosPorLogin.put(usuario.getUsername(), usuario);
		}
	}
	
	public List<Usuario> findUsuarios() throws DAOException {
		return new LinkedList<Usuario>(usuariosPorLogin.values());
	}
	
	public Usuario findUsuario(String login) {
		return usuariosPorLogin.get(login);
	}

	public Usuario findUsuario(int id) {
		return usuariosPorID.get(id);
	}
	
	public void addUsuario(Usuario usuario) {
		usuariosPorID.put(usuario.getId(), usuario);
		usuariosPorLogin.put(usuario.getUsername(), usuario);
	}
	
	public void removeUsuario(Usuario usuario) {
		usuariosPorID.remove(usuario.getId());
		usuariosPorLogin.remove(usuario.getUsername());
	}
	
	
}
