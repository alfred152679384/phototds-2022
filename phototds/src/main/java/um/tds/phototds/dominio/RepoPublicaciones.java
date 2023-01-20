package um.tds.phototds.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import um.tds.phototds.controlador.Controlador;
import um.tds.phototds.dao.DAOException;
import um.tds.phototds.dao.FactoriaDAO;
import um.tds.phototds.dao.PublicacionDAO;

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
	public int getMeGustasPublicacion(int idPubli) {
		return this.publicacionesPorId.get(idPubli).getMeGustas();
	}

	// Funcionalidad
	public List<Publicacion> lookForPublicacion(String h) {
		List<Publicacion> list = new LinkedList<>();
		publicacionesPorHashtag.keySet().stream().filter(k -> k.contains(h))
				.forEach(k -> list.addAll(publicacionesPorHashtag.get(k)));
		return Collections.unmodifiableList(list);
	}

	private void cargarPublicaciones() {
		List<Publicacion> listaPubli = factoria.getPublicacionDAO().getAll();
		listaPubli.stream().forEach(p -> publicacionesPorId.put(p.getId(), p));
	}

	public void addComentario(int idPubli, String comentario) {
		Publicacion p = this.publicacionesPorId.get(idPubli);
		p.addComentario(new Comentario(p.getUsuario(), comentario));
		factoria.getPublicacionDAO().update(p);
	}

	public void darMeGusta(int idPubli) {
		Publicacion p = publicacionesPorId.get(idPubli);
		p.darMeGusta();
		factoria.getPublicacionDAO().update(p);
	}

	public boolean comprobarTituloAlbum(String titulo) {
		return publicacionesPorId.values().stream().map(p -> p.getTitulo()).anyMatch(t -> t.equals(titulo));
	}

	public void addFotosToAlbum(int idAlbum, List<Foto> fList) {
		Album a = (Album) this.publicacionesPorId.get(idAlbum);
		a.addFotos(fList);
		factoria.getPublicacionDAO().addFotosAlbum(idAlbum, fList);
	}
	
	public void addFoto(Usuario usuario, Publicacion p) {
		PublicacionDAO daoFoto = factoria.getPublicacionDAO();
		daoFoto.create(p);
		this.addPublicacion(p);
		factoria.getUsuarioDAO().update(usuario);
	}
	
	public void addAlbum(Usuario usuario, Publicacion p) {
		PublicacionDAO daoAlbum = factoria.getPublicacionDAO();
		daoAlbum.create(p);
		this.addPublicacion(p);
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
	}

	public List<Publicacion> findPublicaciones() throws DAOException {
		return new LinkedList<Publicacion>(publicacionesPorId.values());
	}

	public Optional<Publicacion> findPublicacion(int id) {
		return Optional.ofNullable(publicacionesPorId.get(id));
	}

	public void deletePublicacion(int id) {
		Publicacion p = publicacionesPorId.get(id);
		p.getUsuario().removePublicacion(id);
		publicacionesPorId.remove(id);
		List<String> hashtags;
		if (!(hashtags = p.getHashtags()).isEmpty()) {
			hashtags.stream().forEach(h -> publicacionesPorHashtag.remove(h));
		}
		factoria.getPublicacionDAO().delete(p);
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
	}
}
