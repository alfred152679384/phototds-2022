package um.tds.phototds.dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
	
	private void cargarPublicaciones() {
		System.out.println("cargo publicaciones");
		List<Publicacion> listaPubli = factoria.getPublicacionDAO().getAll();
		for(Publicacion p : listaPubli) {
			System.out.println("-"+p.getTitulo()+"-");
			publicaciones.put(p.getId(), p);
//			if(p instanceof Foto) {
//				fotos.put(p.getId(), (Foto)p);
//			}
//			else {
//				albumes.put(p.getId(), (Album)p);
//			}
		}
	}
	
	public Object[] getFotos() {
		ArrayList<Foto> fotos = new ArrayList<Foto>();
		for(int k : publicaciones.keySet()) {
			Publicacion p = publicaciones.get(k);
			if(p instanceof Foto)
				fotos.add((Foto)p);
		}
		return fotos.toArray();
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
