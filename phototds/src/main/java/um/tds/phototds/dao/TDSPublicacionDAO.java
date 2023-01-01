package um.tds.phototds.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import um.tds.phototds.dominio.Album;
import um.tds.phototds.dominio.Foto;
import um.tds.phototds.dominio.Publicacion;

/**
 * 
 * Clase que implementa tanto el adaptador DAO para Album y Foto
 *
 */
public final class TDSPublicacionDAO implements PublicacionDAO {
	private static final String FOTO = "Foto";
	private static final String ALBUM = "Album";
	private static final String USUARIO = "usuario";
	private static final String TITULO = "titulo";
	private static final String FECHA = "fecha";
	private static final String DESCRIPCION = "descripcion";
	private static final String ME_GUSTAS = "me_gustas";
	private static final String HASHTAGS = "Hashtag";
	private static final String COMENTARIOS = "comentarios";
	private static final String PATH = "path";
	private static final String LISTA_FOTOS = "listaFotos";

	private ServicioPersistencia servPersistencia;
	private SimpleDateFormat dateFormat;

	public TDSPublicacionDAO() {
		try {
			servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}

	private Foto entidadToFoto(Entidad eFoto) {
		// Recuperar fotos del servidor
		String usuario = servPersistencia.recuperarPropiedadEntidad(eFoto, USUARIO);
		String titulo = servPersistencia.recuperarPropiedadEntidad(eFoto, TITULO);
		String fecha = servPersistencia.recuperarPropiedadEntidad(eFoto, FECHA);
		String desc = servPersistencia.recuperarPropiedadEntidad(eFoto, DESCRIPCION);
		String meGustas = servPersistencia.recuperarPropiedadEntidad(eFoto, ME_GUSTAS);
		String hashtags = servPersistencia.recuperarPropiedadEntidad(eFoto, HASHTAGS);
		String comentarios = servPersistencia.recuperarPropiedadEntidad(eFoto, COMENTARIOS);
		String path = servPersistencia.recuperarPropiedadEntidad(eFoto, PATH);

		Foto f = new Foto(usuario, titulo, fecha, desc, meGustas, hashtags, comentarios, path);
		f.setId(eFoto.getId());
		return f;
	}

	private Album entidadToAlbum(Entidad eAlbum) {
		// Recuperar albumes del servidor
		String usuario = servPersistencia.recuperarPropiedadEntidad(eAlbum, USUARIO);
		String titulo = servPersistencia.recuperarPropiedadEntidad(eAlbum, TITULO);
		String fecha = servPersistencia.recuperarPropiedadEntidad(eAlbum, FECHA);
		String desc = servPersistencia.recuperarPropiedadEntidad(eAlbum, DESCRIPCION);
		String meGustas = servPersistencia.recuperarPropiedadEntidad(eAlbum, ME_GUSTAS);
		String hashtags= servPersistencia.recuperarPropiedadEntidad(eAlbum, HASHTAGS);
		String comentarios = servPersistencia.recuperarPropiedadEntidad(eAlbum, COMENTARIOS);
		String listaFotos = servPersistencia.recuperarPropiedadEntidad(eAlbum, LISTA_FOTOS);

		Album a = new Album(usuario, titulo, fecha, desc, meGustas, hashtags, comentarios, listaFotos);
		a.setId(eAlbum.getId());

		return a;
	}

	private Entidad fotoToEntidad(Foto f) {
		Entidad eFoto = new Entidad();
		eFoto.setNombre(FOTO);

		eFoto.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(
						new Propiedad(USUARIO, f.getUsuarioDAO()),
						new Propiedad(TITULO, f.getTitulo()), 
						new Propiedad(FECHA, f.getFechaDAO()),
						new Propiedad(DESCRIPCION, f.getDescripcion()), 
						new Propiedad(ME_GUSTAS, f.getMegustasDAO()),
						new Propiedad(HASHTAGS, f.getHashtagsDAO()),
						new Propiedad(COMENTARIOS, f.getComentariosDAO()), 
						new Propiedad(PATH, f.getPath()))));
		return eFoto;
	}

	private Entidad albumToEntidad(Album a) {
		Entidad eAlbum = new Entidad();
		eAlbum.setNombre(FOTO);

		eAlbum.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(USUARIO, a.getUsuarioDAO()),
				new Propiedad(TITULO, a.getTitulo()),
				new Propiedad(FECHA, a.getFechaDAO()),
				new Propiedad(DESCRIPCION, a.getDescripcion()),
				new Propiedad(ME_GUSTAS, a.getMegustasDAO()),
				new Propiedad(HASHTAGS, a.getHashtagsDAO()),
				new Propiedad(COMENTARIOS, a.getComentariosDAO()),
				new Propiedad(LISTA_FOTOS, a.getListaFotosString())
				)));
		return eAlbum;
	}

	public void create(Publicacion p) {
		if (p instanceof Foto) {
			Entidad eFoto = this.fotoToEntidad((Foto) p);
			eFoto = servPersistencia.registrarEntidad(eFoto);
			p.setId(eFoto.getId());
			return;
		}
		Entidad eAlbum = this.albumToEntidad((Album) p);
		eAlbum = servPersistencia.registrarEntidad(eAlbum);
		p.setId(eAlbum.getId());
	}

	public boolean delete(Publicacion p) {
		Entidad ePubli;
		ePubli = servPersistencia.recuperarEntidad(p.getId());
		return servPersistencia.borrarEntidad(ePubli);
	}

	public void update(Publicacion pb) {
		Entidad ePubli = servPersistencia.recuperarEntidad(pb.getId());

		for (Propiedad p : ePubli.getPropiedades()) {
			if (p.getNombre().equals(TITULO)) {
				p.setValor(pb.getTitulo());
			} else if (p.getNombre().equals(DESCRIPCION)) {
				p.setValor(pb.getDescripcion());
			} else if(p.getNombre().equals(ME_GUSTAS)) {
				p.setValor(pb.getMegustasDAO());
			} else if(p.getNombre().equals(COMENTARIOS)) {
				p.setValor(pb.getComentariosDAO());
			}
			servPersistencia.modificarPropiedad(p);
		}
	}

	public Publicacion get(int id) {
		Entidad ePubli = servPersistencia.recuperarEntidad(id);
		if (ePubli.getNombre().equals(ALBUM)) {
			return entidadToAlbum(ePubli);
		}
		return entidadToFoto(ePubli);
	}

	public List<Publicacion> getAll() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades(FOTO);
		entidades.addAll(servPersistencia.recuperarEntidades(ALBUM));
		List<Publicacion> publicaciones = new LinkedList<Publicacion>();

		for (Entidad e : entidades) {
			publicaciones.add(get(e.getId()));
		}
		return publicaciones;
	}

//	public List<Foto> getAllFotos(){
//		List<Entidad> entidades = servPersistencia.recuperarEntidades(FOTO);
//		List<Foto> fotos = new LinkedList<Foto>();
//		for(Entidad e : entidades) {
//			fotos.add((Foto)get(e.getId()));
//		}
//		return fotos;
//	}
//	
//	public List<Album> getAllAlbumes(){
//		List<Entidad> entidades = servPersistencia.recuperarEntidades(ALBUM);
//		List<Album> albumes = new LinkedList<Album>();
//		for(Entidad e: entidades) {
//			albumes.add((Album)get(e.getId()));
//		}
//		return albumes;
//	}
}
