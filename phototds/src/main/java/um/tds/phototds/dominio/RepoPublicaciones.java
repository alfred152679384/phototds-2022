package um.tds.phototds.dominio;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import um.tds.phototds.dao.DAOException;
import um.tds.phototds.dao.FactoriaDAO;

public enum RepoPublicaciones {
	INSTANCE; // Singleton

	// Atributos
	private FactoriaDAO factoria;
	private HashMap<Integer, Publicacion> publicacionesPorId;
	private HashMap<String, List<Publicacion>> publicacionesPorHashtag;

	// Constructor privado (singleton)
	private RepoPublicaciones() {
		publicacionesPorId = new HashMap<Integer, Publicacion>();
		publicacionesPorHashtag = new HashMap<String, List<Publicacion>>();

		try {
			factoria = FactoriaDAO.getInstancia();
			cargarPublicaciones();

		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	// Getters & Setters
	public List<Publicacion> getPublicacionesSubidas(){
		return this.publicacionesPorId.values().stream().collect(Collectors.toList());
	}
	
	// Funcionalidad
	private void cargarPublicaciones() {
		List<Publicacion> listaPubli = factoria.getPublicacionDAO().getAll();
		listaPubli.stream().forEach(p -> publicacionesPorId.put(p.getId(), p));
	}

	public List<Publicacion> lookForPublicacion(String h) {
		List<Publicacion> list = new LinkedList<>();
		publicacionesPorHashtag.keySet().stream().filter(k -> k.contains(h))
				.forEach(k -> list.addAll(publicacionesPorHashtag.get(k)));
		return list;
	}
	
	public void cargarPublicacionesUsuarios() {
		// Cargamos las publicaciones en los usuarios
		List<Usuario> users = RepoUsuarios.INSTANCE.getUsuariosRegistrados();
		users.stream().forEach(u -> u.cargarPublicaciones(this.publicacionesPorId.values().stream()
				.filter(p -> p.getUsuario().getUsername().equals(u.getUsername())).collect(Collectors.toList())));

		// Cargamos los hashtags
		for (Publicacion p : publicacionesPorId.values()) {
			for (String h : p.getHashtags()) {
				if (!publicacionesPorHashtag.containsKey(h)) {
					List<Publicacion> list = new LinkedList<>();
					list.add(p);
					publicacionesPorHashtag.put(h, list);
				} else
					publicacionesPorHashtag.get(h).add(p);
			}
		}
	}//TODO Dar una vuelta a esto cuando estemos haciendo lo de la persistencia

	public Optional<Publicacion> findPublicacion(int id) {
		return Optional.ofNullable(publicacionesPorId.get(id));
	}

	public void addPublicacion(Publicacion p) {
		publicacionesPorId.put(p.getId(), p);
		for (String h : p.getHashtags()) {
			if (!publicacionesPorHashtag.containsKey(h)) {
				List<Publicacion> list = new LinkedList<>();
				list.add(p);
				publicacionesPorHashtag.put(h, list);
			} else {
				publicacionesPorHashtag.get(h).add(p);
			}
		}
	}
	
	public void removePublicacion(Publicacion p) {
		publicacionesPorId.remove(p.getId());
		List<String> hashtags;
		if (!(hashtags = p.getHashtags()).isEmpty()) {
			hashtags.stream().forEach(h -> publicacionesPorHashtag.get(h).remove(p));
		}
	}
}