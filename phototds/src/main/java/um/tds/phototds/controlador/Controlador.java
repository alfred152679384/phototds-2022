package um.tds.phototds.controlador;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import um.tds.phototds.dao.*;
import um.tds.phototds.dominio.*;
import umu.tds.fotos.ComponenteCargadorFotos;
import umu.tds.fotos.Fotos;
import umu.tds.fotos.FotosEvent;
import umu.tds.fotos.FotosListener;

public enum Controlador implements FotosListener{
	INSTANCE;// Singleton

	// Constantes
	public static final DateTimeFormatter HUMAN_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	public static final int PRECIO_PREMIUM = 5;// 5€

	// Atributos
	private Usuario usuario;
	private FactoriaDAO factoria;

	// Contructor privado (singleton)
	private Controlador() {
		try {
			factoria = FactoriaDAO.getInstancia();
			ComponenteCargadorFotos.INSTANCE.addFotoListener(this);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	// Getters & Setters
	public String getUsuarioActual() {
		return this.usuario.getUsername();
	}

	public String getCorreoUsuarioActual() {
		return this.usuario.getEmail();
	}

	public int getMeGustasFoto(int idPubli) {
		return RepoPublicaciones.INSTANCE.getMeGustasPublicacion(idPubli);
	}

	public List<ComunicacionConGUI> getFotosPrincipal() {
		List<ComunicacionConGUI> listCom = new LinkedList<>();
		List<Foto> listFoto = usuario.getFotosPrincipal();
		for (Foto f : listFoto) {
			ComGUIBuilder b = new ComGUIBuilder();
			b.buildIdPublicacion(f.getId());
			b.buildUsername(f.getUsuario().getUsername());
			b.buildPathFoto(f.getPath());
			b.buildFecha(f.getFecha());
			b.buildMeGustas(f.getMeGustas());
			b.buildTitulo(f.getTitulo());
			b.buildDescripcion(f.getDescripcion());
			listCom.add(b.getResult());
		}
		return listCom;
	}

	public List<ComunicacionConGUI> getFotosPerfil(String username) {
		List<Foto> listFoto = RepoUsuarios.INSTANCE.getFotosPerfil(username);
		List<ComunicacionConGUI> listCom = new LinkedList<>();
		for (Foto f : listFoto) {
			ComGUIBuilder b = new ComGUIBuilder();
			b.buildUsername(username);
			b.buildIdPublicacion(f.getId());
			b.buildPathFoto(f.getPath());
			listCom.add(b.getResult());
		}
		return listCom;
	}

	public List<ComunicacionConGUI> getAlbumesPerfil(String username) {
		List<Album> listAlbum = RepoUsuarios.INSTANCE.getAlbumesPerfil(username);
		List<ComunicacionConGUI> comListAlbum = new LinkedList<>();
		for (Album a : listAlbum) {
			List<ComunicacionConGUI> comListFoto = new LinkedList<>();
			for (Foto f : a.getListaFotos()) {
				ComGUIBuilder b = new ComGUIBuilder();
				b.buildIdPublicacion(f.getId());
				b.buildPathFoto(f.getPath());
				comListFoto.add(b.getResult());
			}
			ComGUIBuilder c = new ComGUIBuilder();
			c.buildIdPublicacion(a.getId());
			c.buildUsername(username);
			c.buildTitulo(a.getTitulo());
			c.buildDescripcion(a.getDescripcion());
			c.buildFotosAlbumes(comListFoto);
			comListAlbum.add(c.getResult());
		}
		return comListAlbum;
	}

	public String getFotoPerfilUsuarioActual() {
		return usuario.getFotoPerfil();
	}

	public String getFotoPerfilUsuario(String username) {
		return RepoUsuarios.INSTANCE.getFotoPerfilUsuario(username);
	}

	public String getCorreoUsuario(String username) {
		return RepoUsuarios.INSTANCE.getCorreoUsuario(username);
	}

	public Collection<Usuario> getUsuariosRegistrados() {
		return RepoUsuarios.INSTANCE.getUsuariosRegistrados();
	}

	public String getPresentacionUsuarioActual() {
		Optional<String> p = usuario.getPresentacion();
		if (p.isEmpty())
			return "";
		return p.get();
	}

	public boolean isUsuarioActualPremium() {
		return this.usuario.isPremium();
	}

	public int getNumPublicaciones(String username) {
		return RepoUsuarios.INSTANCE.getNumPublicacionesUsuario(username);
	}

	public int getNumSeguidores(String username) {
		return RepoUsuarios.INSTANCE.getNumSeguidores(username);
	}

	public int getNumSeguidos(String username) {
		return RepoUsuarios.INSTANCE.getNumSeguidos(username);
	}

	public String getNombreUsuario(String username) {
		return RepoUsuarios.INSTANCE.getNombreUsuario(username);
	}

	// Funcionalidad
	public boolean loginUser(String username, String p) {
		Optional<Usuario> aux = RepoUsuarios.INSTANCE.findUsuario(username);
		if (aux.isPresent() && aux.get().getPassword().equals(p)
				|| aux.isPresent() && aux.get().getPassword().equals(p)) {
			this.usuario = aux.get();
			RepoPublicaciones.INSTANCE.cargarPublicacionesUsuarios();
			RepoUsuarios.INSTANCE.cargarNotificaciones();
			return true;
		}
		return false;
	}
	
	public List<ComunicacionConGUI> getNotificacionesUsuarioActual(){
		List<ComunicacionConGUI> comList = new LinkedList<>();
		List<Notificacion> notifs = RepoUsuarios.INSTANCE.getNotificaciones(usuario);
		for(Notificacion n : notifs) {
			ComGUIBuilder b = new ComGUIBuilder();
			b.buildUsername(n.getPublicacion().getUsuario().getUsername());
			b.buildTitulo(n.getPublicacion().getTitulo());
			b.buildFecha(n.getFechaPublicacion());
			comList.add(b.getResult());
		}
		return comList;
	}

	public boolean registerUser(String username, String nombre, String email, String cont, String fechN,
			Optional<String> fotoPerfil, Optional<String> presentacion) {
		Optional<Usuario> optUser = RepoUsuarios.INSTANCE.findUsuario(username);
		if (optUser.isPresent()) {
			return false;
		}
		// Creamos el usuario en el dominio
		Usuario u = new Usuario(username, nombre, email, cont, fechN, fotoPerfil, presentacion);

		// Añadimos al usuario al servidor de Persistencia
		UsuarioDAO daoUser = factoria.getUsuarioDAO();
		daoUser.create(u);
		RepoUsuarios.INSTANCE.addUsuario(u);
		return true;
	}

	public void updateUser(Optional<String> password, Optional<String> fotoPerfil, Optional<String> presentacion) {
		if (password.isPresent())
			usuario.setPassword(password.get());
		if (fotoPerfil.isPresent())
			usuario.setFotoPerfil(fotoPerfil.get());
		if (presentacion.isPresent())
			usuario.setPresentacion(presentacion.get());

		factoria.getUsuarioDAO().update(usuario);
	}

	public void seguirUsuario(String username) {
		RepoUsuarios.INSTANCE.seguirUsuario(this.usuario, username);
	}

	public boolean addFoto(String path, String title, String desc) {
		Publicacion p = new Foto(this.usuario, title, desc, path);
		PublicacionDAO daoFoto = factoria.getPublicacionDAO();
		daoFoto.create(p);
		RepoPublicaciones.INSTANCE.addPublicacion(p);
		RepoUsuarios.INSTANCE.addPublicacion(usuario, p);
		factoria.getUsuarioDAO().update(usuario);
		return true;
	}
	
	public void addAlbum(String titulo, String descripcion, List<ComunicacionConGUI> comList) {
		Publicacion p = new Album(usuario, titulo, descripcion, comList);
		PublicacionDAO daoAlbum = factoria.getPublicacionDAO();
		daoAlbum.create(p);
		RepoPublicaciones.INSTANCE.addPublicacion(p);
		RepoUsuarios.INSTANCE.addPublicacion(usuario, p);
	}

	public void addFotosToAlbum(int idAlbum, List<ComunicacionConGUI> comList) {
		List<Foto> listFotos = new LinkedList<>();
		for (ComunicacionConGUI c : comList) {
			Foto f = new Foto(usuario, c.getTitulo(), c.getDescripcion(), c.getPathFoto());
			listFotos.add(f);
		}
		RepoPublicaciones.INSTANCE.addFotosToAlbum(idAlbum, listFotos);
	}

	public boolean comprobarTituloAlbum(String tit) {
		return RepoPublicaciones.INSTANCE.comprobarTituloAlbum(tit);
	}

	public Optional<Usuario> findUsuario(String username) {
		return RepoUsuarios.INSTANCE.findUsuario(username);
	}

	public Optional<Publicacion> findPublicacion(int id) {
		return RepoPublicaciones.INSTANCE.findPublicacion(id);
	}

	public void darMeGusta(int idPublicacion) {
		RepoPublicaciones.INSTANCE.darMeGusta(idPublicacion);
	}

	public void escribirComentario(int idPubli, String coment) {
		RepoPublicaciones.INSTANCE.addComentario(idPubli, coment);
	}

	public List<ComunicacionConGUI> getTopMeGustasUsuarioActual() {
		List<ComunicacionConGUI> comList = new LinkedList<>();
		List<Foto> fotos = usuario.getFotosPerfil().stream().sorted((o1, o2) -> Integer.compare(o2.getMeGustas(), o1.getMeGustas()))
				.limit(10).collect(Collectors.toList());

		for(Foto f : fotos) {
			ComGUIBuilder b = new ComGUIBuilder();
			b.buildPathFoto(f.getPath());
			b.buildMeGustas(f.getMeGustas());
			comList.add(b.getResult());
		}
		return comList;
	}

	public void setUsuarioActualPremium() {
		usuario.setPremium(true);
		factoria.getUsuarioDAO().update(usuario);
	}

	public void deletePublicacion(int idPublicacion) {
		RepoPublicaciones.INSTANCE.deletePublicacion(idPublicacion);
	}

	public double comprobarDescuento(int mode) {
		switch(mode) {
		case 0:
			usuario.setDescuentoEstrategia(new DescuentoEdadEstrategia(usuario));
			break;
		case 1:
			usuario.setDescuentoEstrategia(new DescuentoMeGustasEstrategia(usuario));
			break;
		default:
			return PRECIO_PREMIUM;
		}
		return usuario.aplicarDescuento();
	}
	
	public void crearExcelSeguidores() {
		GeneradorExcel g = new GeneradorExcel();
		g.generarExcel(usuario.getSeguidores());
	}
	
	public void crearPdfSeguidoeres() {
		GeneradorPDF p =  new GeneradorPDF();
		p.generarPDF(usuario.getSeguidores());
	}
	
	public boolean cargarFotosBean(File f) {
		if(!f.getAbsolutePath().endsWith(".xml")) {
			return false;
		}
		ComponenteCargadorFotos.INSTANCE.setArchivoFoto(f.getAbsolutePath());
		return true;
	}
	
	@Override
	public void notificaNuevasFotos(FotosEvent ev) {
		Fotos fotos = ev.getFotos();

		
		//Metemos las fotos en el usuario
		for(umu.tds.fotos.Foto f : fotos.getFotos()) {
			Publicacion p = new Foto(usuario, f.getTitulo(), f.getDescripcion(), f.getPath());
			f.getHashTags().stream().forEach(h -> h.getHashTag().stream()
					.forEach(ha -> p.addHashTag(ha)));
			
			PublicacionDAO daoFoto = factoria.getPublicacionDAO();
			daoFoto.create(p);
			RepoPublicaciones.INSTANCE.addPublicacion(p);
			RepoUsuarios.INSTANCE.addPublicacion(usuario, p);
			factoria.getUsuarioDAO().update(usuario);
		}
	}

	public List<ComunicacionConGUI> lookFor(String txt) {
		List<ComunicacionConGUI> r = new LinkedList<>();
		if (txt.startsWith("#")) {
			List<Publicacion> l = RepoPublicaciones.INSTANCE.lookForPublicacion(txt);
			if (l.isEmpty())
				return Collections.emptyList();
			for (Publicacion p : l) {
				ComGUIBuilder b = new ComGUIBuilder();
				b.buildNumSeguidoresUsuario(p.getUsuario().getNumSeguidores());
				b.buildUsername(p.getUsuario().getUsername());
				b.buildHashtag(p.getHashtagContaining(txt));
				b.buildMode(ComunicacionConGUI.MODE_BUSQ_HASHTAGS);
				r.add(b.getResult());
			}
			return r;
		}
		List<Usuario> users = RepoUsuarios.INSTANCE.lookForUser(txt);
		if (users.isEmpty())
			return Collections.emptyList();
		users.remove(this.usuario);
		for (Usuario u : users) {
			ComGUIBuilder b = new ComGUIBuilder();
			b.buildPathFoto(u.getFotoPerfil());
			b.buildNombreUsuario(u.getNombre());
			b.buildUsername(u.getUsername());
			b.buildMode(ComunicacionConGUI.MODE_BUSQ_USUARIOS);
			r.add(b.getResult());
		}
		return r;
	}

	public static void setProp(double[] size, double x, double y) {// SetProporcion
		double[] newSize = new double[2];
		newSize[0] = size[0];
		newSize[1] = size[1];
		double prop;
		if (size[0] >= x) {
			prop = size[0] / size[1];
			newSize[0] = x;
			newSize[1] = x / prop;
		}
		if (newSize[1] >= y) {
			prop = size[1] / size[0];
			newSize[1] = y;
			newSize[0] = y / prop;
		}

		size[0] = newSize[0];
		size[1] = newSize[1];
	}

	public static void setProfileProp(double[] size, double w) {// SetProporcion
		double[] newSize = new double[2];
		newSize[0] = size[0];
		newSize[1] = size[1];
		double prop;
		if (size[1] >= size[0]) {// fotos verticales
			prop = size[0] / size[1];
			newSize[0] = w;
			newSize[1] = w / prop;
		} else {// fotos horizontales
			prop = size[1] / size[0];
			newSize[1] = w;
			newSize[0] = w / prop;
		}

		size[0] = newSize[0];
		size[1] = newSize[1];
	}
}
