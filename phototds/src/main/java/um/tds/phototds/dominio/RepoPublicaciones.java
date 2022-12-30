package um.tds.phototds.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import um.tds.phototds.controlador.Controlador;
import um.tds.phototds.dao.DAOException;
import um.tds.phototds.dao.FactoriaDAO;

public enum RepoPublicaciones {
	INSTANCE; //Singleton
	//Atributos
	private FactoriaDAO factoria;
	private HashMap<Integer, Publicacion> publicaciones;
//	private HashMap<Integer, Foto> fotos;
//	private HashMap<Integer, Album> albumes;
	
	//Constructor privado (singleton)
	private RepoPublicaciones() {
		publicaciones = new HashMap<Integer, Publicacion>();
//		fotos = new HashMap<Integer,Foto>();
//		albumes = new HashMap<Integer, Album>();
		
		try {
			factoria = FactoriaDAO.getInstancia();
			cargarPublicaciones();
		} catch(DAOException e) {
			e.printStackTrace();
		}
	}
	
	//Getters & Setters
	public List<Foto> getFotosPrincipal() {//TODO implementar con stream
		ArrayList<Foto> fotos = new ArrayList<Foto>();
		for(int k : publicaciones.keySet()) {
			Publicacion p = publicaciones.get(k);
			if(p instanceof Foto)
				fotos.add((Foto)p);
		}
		return Collections.unmodifiableList(fotos);
	} 
	
//	public List<Foto> getFotosPerfil(String username){ TODO
//		publicaciones.values().stream()
//			.filter(f -> f instanceof Foto)
//			.filter(f -> f.get)
//	}

	//Funcionalidad
	private void cargarPublicaciones() {
		List<Publicacion> listaPubli = factoria.getPublicacionDAO().getAll();
		for(Publicacion p : listaPubli) {
			publicaciones.put(p.getId(), p);
//			if(p instanceof Foto) {
//				fotos.put(p.getId(), (Foto)p);
//			}
//			else {
//				albumes.put(p.getId(), (Album)p);
//			}
		}
		LinkedList<Usuario> users = new LinkedList<>(Controlador.INSTANCE.getUsuariosRegistrados());
		//Cargamos las publicaciones en los usuarios
		for(Usuario u : users) {
			List<Publicacion> publish = this.publicaciones.values().stream()
					.filter(p -> p.getUsuario().getUsername().equals(u))
					.collect(Collectors.toList());
			Controlador.INSTANCE.cargarPublicaciones(u, publish);
		}
	}
		
	public List<Publicacion> findPublicaciones() throws DAOException {
		return new LinkedList<Publicacion>(publicaciones.values());
	}
	
	public Publicacion findPublicacion(int id) {
		return publicaciones.get(id);
	}
	
	public void addPublicacion(Publicacion p) {
		publicaciones.put(p.getId(), p);
	}
	
	public void removePublicacion(Publicacion p) {
		publicaciones.remove(p.getId());
	}
}
