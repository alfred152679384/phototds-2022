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
			usuariosPorEmail.put(usuario.getEmail(), usuario);
			usuariosPorLogin.put(usuario.getUsername(), usuario);
		}
	}
	
	//Getters & Setters
	public List<Usuario> getUsuariosRegistrados(){
		return usuariosPorID.values().stream().collect(Collectors.toList());
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
	
	public List<Foto> getFotosPerfil(String username){
		return usuariosPorLogin.get(username).getFotosPerfil();
	}
	
	public List<Album> getAlbumesPerfil(String username){
		return usuariosPorLogin.get(username).getAlbumesPerfil();
	}
	
	//Funcionalidad
	public Optional<Usuario> findUsuario(String username) {
		return Optional.ofNullable(usuariosPorLogin.get(username));
	}
	
	public void seguirUsuario(Usuario actualUser, String seguirUser) {
		Usuario seguido = this.usuariosPorLogin.get(seguirUser);
		actualUser.seguirUsuario(seguido);
		seguido.addseguidor(actualUser);
		
		//Actualizar en persistencia
		factoria.getUsuarioDAO().update(actualUser);
		factoria.getUsuarioDAO().update(seguido);
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
	
	public void cargarNotificaciones() {
		this.usuariosPorID.values().stream()
			.forEach(u -> u.cargarNotificaciones());
	}
	
	public List<Notificacion> getNotificaciones(Usuario u){
		List<Notificacion> notifList = u.getNotificaciones();
		factoria.getUsuarioDAO().update(u);
		return notifList;
	}
	
	public void addPublicacion(Usuario usuario, Publicacion p) {
		usuario.addPublicacion(p);
		usuario.getSeguidores().stream()
			.forEach(s -> factoria.getUsuarioDAO().update(s));
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