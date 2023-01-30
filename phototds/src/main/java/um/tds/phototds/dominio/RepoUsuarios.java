package um.tds.phototds.dominio;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import um.tds.phototds.dao.*;

public enum RepoUsuarios {
	INSTANCE;// Singleton
	// Atributos

	private FactoriaDAO factoria;
	private HashMap<Integer, Usuario> usuariosPorID;
	private HashMap<String, Usuario> usuariosPorLogin;
	private HashMap<String, Usuario> usuariosPorEmail;

	// Contstructor privado (singleton)
	private RepoUsuarios() {
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

	// Getters & Setters
	public List<Usuario> getUsuariosRegistrados() {
		return usuariosPorID.values().stream().collect(Collectors.toList());
	}

	// Funcionalidad
	public void registrarUsuario(Usuario u) {
		usuariosPorID.put(u.getId(), u);
		usuariosPorLogin.put(u.getUsername(), u);
		usuariosPorEmail.put(u.getEmail(), u);
	}
	
	public Optional<Usuario> findUsuario(String username) {
		return Optional.ofNullable(usuariosPorLogin.get(username));
	}
	
	public Optional<Usuario> findUsuario(int id) {
		return Optional.ofNullable(usuariosPorID.get(id));
	}
	
	public Optional<Usuario> findUsuarioEmail(String email) {
		return Optional.ofNullable(usuariosPorEmail.get(email));
	}
	
	public List<Usuario> buscarUsuario(String user) {
		String aux = user.toLowerCase();
		List<Usuario> returnedList;

		// Probamos con el username
		returnedList = usuariosPorID.values().stream().filter(u -> u.getUsername().toLowerCase().contains(aux))
				.collect(Collectors.toList());

		// Ahora probamos con el nombre propio
		if (returnedList.isEmpty()) {
			returnedList = usuariosPorID.values().stream().filter(u -> u.getNombre().toLowerCase().contains(aux))
					.collect(Collectors.toList());
		}

		// Probamos por ultimo con el email
		if (returnedList.isEmpty()) {
			returnedList = usuariosPorID.values().stream().filter(u -> u.getNombre().toLowerCase().contains(aux))
					.collect(Collectors.toList());
		}
		return returnedList;
	}

	public void removeUsuario(Usuario usuario) {
		usuariosPorEmail.remove(usuario.getEmail());
		usuariosPorID.remove(usuario.getId());
		usuariosPorLogin.remove(usuario.getUsername());
	}

}