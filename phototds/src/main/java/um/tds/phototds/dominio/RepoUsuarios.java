package um.tds.phototds.dominio;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import um.tds.phototds.dao.*;

public enum RepoUsuarios {
	INSTANCE;//Singleton
	//Atributos
	private FactoriaDAO factoria;
	private HashMap<Integer, Usuario> usuariosPorID; 
	private HashMap<String, Usuario> usuariosPorLogin;
	private HashMap<String, Usuario> usuariosPorEmail;
	
	//Contstructor privado (singleton)
	private RepoUsuarios(){
		usuariosPorID = new HashMap<Integer, Usuario>();
		usuariosPorLogin = new HashMap<String, Usuario>();
		usuariosPorEmail = new HashMap<String, Usuario>();
		
		try {
			factoria = FactoriaDAO.getInstancia();
			cargarUsuarios();
			
		} catch (DAOException eDAO) {
			   eDAO.printStackTrace();
		}
	}
	
	private void cargarUsuarios() {
		List<Usuario> listaUsuarios = factoria.getUsuarioDAO().getAll();
		for (Usuario usuario : listaUsuarios) {
			usuariosPorID.put(usuario.getId(), usuario);
			usuariosPorLogin.put(usuario.getUsername(), usuario);
			usuariosPorEmail.put(usuario.getEmail(), usuario);
		}
		for(Usuario u: listaUsuarios) {
			u.cargarListasUsuarios();
		}
	}
	
	public Collection<Usuario> getUsuariosRegistrados(){
		return Collections.unmodifiableCollection(this.usuariosPorID.values());
	}
	
	public Optional<Usuario> findUsuario(String username) {
//		usuariosPorLogin.values().stream()
//			.map(u -> u.getUsername())
//			.forEach(u -> System.out.println(u));
		return Optional.ofNullable(usuariosPorLogin.get(username));
	}
	
	public Optional<Usuario> findUsuarioEmail(String email) {
		return Optional.ofNullable(usuariosPorEmail.get(email));
	}
	
	public Optional<Usuario> findUsuario(int id) {
		return Optional.ofNullable(usuariosPorID.get(id));
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