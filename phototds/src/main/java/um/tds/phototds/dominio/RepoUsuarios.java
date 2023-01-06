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
import java.util.stream.Collectors;

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

	//Getters & Setters
	public Collection<Usuario> getUsuariosRegistrados(){
		return Collections.unmodifiableCollection(this.usuariosPorID.values());
	}
	
	public String getFotoPerfilUsuario(String username) {
		return this.usuariosPorLogin.get(username).getFotoPerfil();
	}
	
	public String getCorreoUsuario(String username) {
		return this.usuariosPorLogin.get(username).getEmail();
	}
	
	public int getNumPublicacionesUsuario(String username) {
		return this.usuariosPorLogin.get(username).getNumPublicaciones();
	}
	
	public int getNumSeguidores(String username) {
		return this.usuariosPorLogin.get(username).getNumSeguidores();
	}
	
	public int getNumSeguidos(String username) {
		return this.usuariosPorLogin.get(username).getNumSeguidos();
	}
	
	public String getNombreUsuario(String username) {
		return this.usuariosPorLogin.get(username).getNombre();
	}
	
	//Funcionalidad
	public Optional<Usuario> findUsuario(String username) {
		return Optional.ofNullable(usuariosPorLogin.get(username));
	}
	
	public List<Usuario> lookForUser(String txt){
		String aux = txt.toLowerCase();
		List<Usuario> list;
		//Probamos con el username
		list = usuariosPorID.values().stream()
				.filter(u -> u.getUsername().toLowerCase().contains(aux))
				.collect(Collectors.toList());

		//Ahora probamos con el nombre propio
		if(list.isEmpty()) {
			list = usuariosPorID.values().stream()
					.filter(u -> u.getNombre().toLowerCase().contains(aux))
					.collect(Collectors.toList());
		}

		//Probamos por ultimo con el email
		if(list.isEmpty()) {
			list = usuariosPorID.values().stream()
					.filter(u -> u.getNombre().toLowerCase().contains(aux))
					.collect(Collectors.toList());
		}
		return list;
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