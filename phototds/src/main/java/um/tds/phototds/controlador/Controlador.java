package um.tds.phototds.controlador;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import um.tds.phototds.dominio.*;
import um.tds.phototds.dao.DAOException;
import um.tds.phototds.dao.FactoriaDAO;
import um.tds.phototds.dao.PublicacionDAO;
import um.tds.phototds.dao.UsuarioDAO;
import umu.tds.fotos.ComponenteCargadorFotos;
import umu.tds.fotos.Fotos;
import umu.tds.fotos.FotosEvent;
import umu.tds.fotos.FotosListener;

public enum Controlador implements FotosListener {
	INSTANCE;// Singleton

	// Constantes
	public static final DateTimeFormatter HUMAN_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	public static final int PRECIO_PREMIUM = 5;// 5€

	// Atributos
	private Usuario usuario;
	private FactoriaDAO factoria;

	// Contructor privado (singleton)
	private Controlador() {
		// Nos añadimos a la lista de oyentes del cargador Fotos
		ComponenteCargadorFotos.INSTANCE.addFotoListener(this);
		try {
			factoria = FactoriaDAO.getInstancia();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	// Getters & Setters
	public Usuario getUsuarioActual() {
		return this.usuario;
	}

//	public Collection<Usuario> getUsuariosRegistrados() {
//		return RepoUsuarios.INSTANCE.getUsuariosRegistrados();
//	}

	// Funcionalidad
	public boolean loginUser(String username, String p) {
		Optional<Usuario> aux = RepoUsuarios.INSTANCE.findUsuario(username);
		if (aux.isEmpty())
			aux = RepoUsuarios.INSTANCE.findUsuarioEmail(username);

		if (aux.isPresent() && aux.get().getPassword().equals(p)) {
			this.usuario = aux.get();
			RepoPublicaciones.INSTANCE.cargarPublicacionesUsuarios();// TODO mejorar persistencia
			RepoUsuarios.INSTANCE.cargarNotificaciones();
			return true;
		}
		return false;
	}

	public boolean registerUser(String username, String nombre, String email, String cont, String fechN,
			Optional<String> fotoPerfil, Optional<String> presentacion) {
		if (RepoUsuarios.INSTANCE.findUsuario(username).isPresent()
				|| RepoUsuarios.INSTANCE.findUsuarioEmail(email).isPresent())
			return false;

		// Creamos el usuario en el dominio
		Usuario u = new Usuario(username, nombre, email, cont, fechN, fotoPerfil, presentacion);

		//Añadimos usuario a repositorio
		RepoUsuarios.INSTANCE.registrarUsuario(u);
		
		//Añadimos usuario a persistencia
		UsuarioDAO daoUser = factoria.getUsuarioDAO();
		daoUser.create(u);

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

	public void seguirUsuario(Usuario userSeguido) {
		usuario.seguirUsuario(userSeguido);
		userSeguido.addseguidor(usuario);

		// Actualizar en persistencia
		factoria.getUsuarioDAO().update(usuario);
		factoria.getUsuarioDAO().update(userSeguido);
	}
	
	public List<Notificacion> getNotificaciones(Usuario u){
		List<Notificacion> returnedList = u.getNotificaciones();
		factoria.getUsuarioDAO().update(u);
		return returnedList;
	}

	public void addFoto(String path, String title, String desc) {
		// Creamos la Foto
		Foto f = usuario.addFoto(title, desc, path);
		//Añadimos a repositorio y persistencia
		addPublicacion(f);
	}

	public void addAlbum(String titulo, String descripcion, List<String> fTitulo, List<String> fDesc,
			List<String> fPath) {
		// Creamos el album
		Album a = usuario.addAlbum(titulo, descripcion, fTitulo, fDesc, fPath);
		//Añadimos a repositorio y persistencia
		addPublicacion(a);
	}
	
	private void addPublicacion(Publicacion p) {
		// Añadimos a repositorio
		RepoPublicaciones.INSTANCE.addPublicacion(p);

		// Añadimos a persistencia
		PublicacionDAO daoFoto = factoria.getPublicacionDAO();
		daoFoto.create(p);
		factoria.getUsuarioDAO().update(usuario);
		
		//Actualizamos las notificaciones de los usuarios seguidores
		usuario.getSeguidores().stream()
			.forEach(s -> factoria.getUsuarioDAO().update(s));
	}

	public void addFotosToAlbum(Album a, List<String> fTitulo, List<String> fDesc, List<String> fPath) {
		List<Foto> nuevasFotos = usuario.addFotosToAlbum(a, fTitulo, fDesc, fPath);
		factoria.getPublicacionDAO().addFotosAlbum(a.getId(), nuevasFotos);
		factoria.getPublicacionDAO().update(a);
	}

	public boolean comprobarTituloAlbum(String tit) {
		return usuario.getAlbumesPerfil().stream().anyMatch(a -> a.getTitulo().equals(tit));
	}

	public Optional<Usuario> findUsuario(String username) {
		return RepoUsuarios.INSTANCE.findUsuario(username);
	}//TODO mejora persistencia

	public Optional<Publicacion> findPublicacion(int id) {
		return RepoPublicaciones.INSTANCE.findPublicacion(id);
	}//TODO idem

	public void darMeGusta(Publicacion p) {
		p.darMeGusta();
		factoria.getPublicacionDAO().update(p);
	}

	public void escribirComentario(Foto f, String coment) {
		f.addComentario(usuario, coment);
		factoria.getPublicacionDAO().update(f);
	}

	public void setUsuarioActualPremium() {
		usuario.setPremium(true);
		factoria.getUsuarioDAO().update(usuario);
	}

	public void deletePublicacion(Publicacion p) {
		usuario.removePublicacion(p);
		RepoPublicaciones.INSTANCE.removePublicacion(p);
		factoria.getPublicacionDAO().delete(p);
	}

	public double comprobarDescuento(int mode) {
		switch (mode) {
		case 0:
			usuario.setDescuentoEstrategia(new DescuentoEdadEstrategia(usuario));
			break;
		case 1:
			usuario.setDescuentoEstrategia(new DescuentoMeGustasEstrategia(usuario));
			break;
		default:
			return PRECIO_PREMIUM;
		}
		return usuario.getPrecio();
	}

	public void crearExcelSeguidores() {
		GeneradorExcel g = new GeneradorExcel();
		g.generarExcel(usuario.getSeguidores());
	}

	public void crearPdfSeguidoeres() {
		GeneradorPDF p = new GeneradorPDF();
		p.generarPDF(usuario.getSeguidores());
	}

	public boolean cargarFotosBean(File f) {
		if (!f.getAbsolutePath().endsWith(".xml")) {
			return false;
		}
		ComponenteCargadorFotos.INSTANCE.setArchivoFoto(f.getAbsolutePath());
		return true;
	}

	@Override
	public void notificaNuevasFotos(FotosEvent ev) {
		Fotos fotos = ev.getFotos();
		// Metemos las fotos en el usuario
		for (umu.tds.fotos.Foto f : fotos.getFotos()) {
			// Creamos la Foto
			Publicacion p = usuario.addFoto(f.getTitulo(), f.getDescripcion(), f.getPath());
			f.getHashTags().stream().forEach(h -> h.getHashTag().stream().forEach(ha -> p.addHashTag(ha)));

			// Añadimos a repositorio
			RepoPublicaciones.INSTANCE.addPublicacion(p);

			// Añadimos a persistencia
			PublicacionDAO daoFoto = factoria.getPublicacionDAO();
			daoFoto.create(p);
			factoria.getUsuarioDAO().update(usuario);
		}
	}

	public List<Publicacion> buscarPublicacion(String h) {
		return RepoPublicaciones.INSTANCE.lookForPublicacion(h);
	}

	public List<Usuario> buscarUsuario(String user) {
		List<Usuario> users = RepoUsuarios.INSTANCE.buscarUsuario(user);
		users.remove(usuario);
		return users;
	}
}