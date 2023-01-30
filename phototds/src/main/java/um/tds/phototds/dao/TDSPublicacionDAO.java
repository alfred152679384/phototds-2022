package um.tds.phototds.dao;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import um.tds.phototds.dominio.Album;
import um.tds.phototds.dominio.Comentario;
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
	private static final String FOTO_ALBUM = "FotoAlbum";
	private static final String ALBUM_ID = "AlbumId";
	private static final String USUARIO = "usuario";
	private static final String TITULO = "titulo";
	private static final String FECHA = "fecha";
	private static final String DESCRIPCION = "descripcion";
	private static final String ME_GUSTAS = "me_gustas";
	private static final String HASHTAGS = "Hashtag";
	private static final String COMENTARIOS = "comentarios";
	private static final String PATH = "path";
	private static final String LISTA_FOTOS = "listaFotos";

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
	private static final String LISTA_VACIA = "[]";

	private ServicioPersistencia servPersistencia;

	public TDSPublicacionDAO() {
		try {
			servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Foto entidadToFoto(Entidad eFoto) {
		// Recuperar fotos del servidor
		String usuarioString = servPersistencia.recuperarPropiedadEntidad(eFoto, USUARIO);
		String titulo = servPersistencia.recuperarPropiedadEntidad(eFoto, TITULO);
		String fechaString = servPersistencia.recuperarPropiedadEntidad(eFoto, FECHA);
		LocalDateTime fecha = LocalDateTime.parse(fechaString, FORMATTER);
		
		String desc = servPersistencia.recuperarPropiedadEntidad(eFoto, DESCRIPCION);
		String meGustasString = servPersistencia.recuperarPropiedadEntidad(eFoto, ME_GUSTAS);
		int meGustas = Integer.parseInt(meGustasString);
		
		String hashtagsString = servPersistencia.recuperarPropiedadEntidad(eFoto, HASHTAGS);
		List<String> hashtags = hashtagToList(hashtagsString);
		
		String comentariosString = servPersistencia.recuperarPropiedadEntidad(eFoto, COMENTARIOS);
		String path = servPersistencia.recuperarPropiedadEntidad(eFoto, PATH);

		Foto f = new Foto(usuarioString, titulo, fecha, desc, meGustas, hashtags, comentariosString, path);
		f.setId(eFoto.getId());
		return f;
	}

	private Album entidadToAlbum(Entidad eAlbum) {
		// Recuperar albumes del servidor
		String usuarioString = servPersistencia.recuperarPropiedadEntidad(eAlbum, USUARIO);
		String titulo = servPersistencia.recuperarPropiedadEntidad(eAlbum, TITULO);
		String fechaString = servPersistencia.recuperarPropiedadEntidad(eAlbum, FECHA);
		LocalDateTime fecha = LocalDateTime.parse(fechaString, FORMATTER);
		
		String desc = servPersistencia.recuperarPropiedadEntidad(eAlbum, DESCRIPCION);
		String meGustasString = servPersistencia.recuperarPropiedadEntidad(eAlbum, ME_GUSTAS);
		int meGustas = Integer.parseInt(meGustasString);
		
		String hashtagsString = servPersistencia.recuperarPropiedadEntidad(eAlbum, HASHTAGS);
		List<String> hashtags = hashtagToList(hashtagsString);
		
		String comentariosString = servPersistencia.recuperarPropiedadEntidad(eAlbum, COMENTARIOS);
		String listaFotos = servPersistencia.recuperarPropiedadEntidad(eAlbum, LISTA_FOTOS);

		Album a = new Album(usuarioString, titulo, fecha, desc, meGustas, hashtags, comentariosString, listaFotos);
		a.setId(eAlbum.getId());

		return a;
	}

	private Entidad fotoToEntidad(Foto f) {
		Entidad eFoto = new Entidad();
		eFoto.setNombre(FOTO);
		
		eFoto.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
						new Propiedad(USUARIO, f.getUsuario().getUsername()), 
						new Propiedad(TITULO, f.getTitulo()),
						new Propiedad(FECHA, f.getFecha().format(FORMATTER)), 
						new Propiedad(DESCRIPCION, f.getDescripcion()),
						new Propiedad(ME_GUSTAS, Integer.toString(f.getMeGustas())), 
						new Propiedad(HASHTAGS, listHashtagsToString(f.getHashtags())),
						new Propiedad(COMENTARIOS, Comentario.comentariosToString(f.getComentarios())), 
						new Propiedad(PATH, f.getPath()))));
		return eFoto;
	}

	private Entidad fotoAlbumToEntidad(Album a, Foto f) {
		Entidad eFoto = new Entidad();
		eFoto.setNombre(FOTO_ALBUM);

		eFoto.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(ALBUM_ID, Integer.toString(a.getId())),
				new Propiedad(USUARIO, f.getUsuario().getUsername()), 
				new Propiedad(TITULO, f.getTitulo()),
				new Propiedad(FECHA, f.getFecha().format(FORMATTER)),
				new Propiedad(DESCRIPCION, f.getDescripcion()),
				new Propiedad(ME_GUSTAS, Integer.toString(f.getMeGustas())), 
				new Propiedad(HASHTAGS, listHashtagsToString(f.getHashtags())),
				new Propiedad(COMENTARIOS, Comentario.comentariosToString(f.getComentarios())), 
				new Propiedad(PATH, f.getPath()))));
		return eFoto;
	}

	private Entidad albumToEntidad(Album a) {
		Entidad eAlbum = new Entidad();
		eAlbum.setNombre(ALBUM);

		eAlbum.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(USUARIO, a.getUsuario().getUsername()),
				new Propiedad(TITULO, a.getTitulo()), 
				new Propiedad(FECHA, a.getFecha().format(FORMATTER)),
				new Propiedad(DESCRIPCION, a.getDescripcion()), 
				new Propiedad(ME_GUSTAS, Integer.toString(a.getMeGustas())),
				new Propiedad(HASHTAGS, listHashtagsToString(a.getHashtags())), 
				new Propiedad(COMENTARIOS, Comentario.comentariosToString(a.getComentarios())))));
		return eAlbum;
	}

	private void registrarFotosAlbum(Album a) {
		for (Foto f : a.getListaFotos()) {
			Entidad eFoto = fotoAlbumToEntidad(a, f);
			eFoto = servPersistencia.registrarEntidad(eFoto);
			f.setId(eFoto.getId());
		}
	}

	public void addFotosAlbum(Album a, List<Foto> fList) {
		for(Foto f : fList) {
			Entidad eFoto = fotoAlbumToEntidad(a, f);
			eFoto = servPersistencia.registrarEntidad(eFoto);
			f.setId(eFoto.getId());
		}
	}

	public void create(Publicacion p) {
		if (p instanceof Foto) {
			Entidad eFoto = this.fotoToEntidad((Foto) p);
			eFoto = servPersistencia.registrarEntidad(eFoto);
			p.setId(eFoto.getId());
			return;
		}
		// Album
		Entidad eAlbum = this.albumToEntidad((Album) p);
		eAlbum = servPersistencia.registrarEntidad(eAlbum);
		p.setId(eAlbum.getId());
		registrarFotosAlbum((Album) p);
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
			} else if (p.getNombre().equals(ME_GUSTAS)) {
				p.setValor(Integer.toString(pb.getMeGustas()));
			} else if (p.getNombre().equals(COMENTARIOS)) {
				p.setValor(Comentario.comentariosToString(pb.getComentarios()));
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
			Publicacion p = get(e.getId());
			if (e.getNombre().equals(ALBUM)) {
				Album a = (Album) p;
				// Recorrer fotosAlbumes añadir al album si coincide el idAlbum
				servPersistencia.recuperarEntidades(FOTO_ALBUM).stream()
					.filter(en -> servPersistencia.recuperarPropiedadEntidad(en, ALBUM_ID).equals(Integer.toString(e.getId())))
					.forEach(en -> a.addFotoDAO((Foto) get(en.getId())));
			}
			publicaciones.add(p);
		}
		return publicaciones;
	}
	
	private List<String> hashtagToList(String s) {
		if (s.equals(LISTA_VACIA))
			return Collections.emptyList();
		List<String> list = new LinkedList<>();
		String aux = s.substring(1, s.length() - 1);
		String[] l = aux.split(",");
		for (int i = 0; i < l.length; i++) {
			list.add(l[i]);
		}
		return list;
	}
	
	private String listHashtagsToString(List<String> hashtags) {
		String l = "[";
		for (int i = 0; i < hashtags.size(); i++) {
			if (i == 0)
				l += hashtags.get(i);
			else
				l += "," + hashtags.get(i);
		}
		l += "]";
		return l;
	}
}
