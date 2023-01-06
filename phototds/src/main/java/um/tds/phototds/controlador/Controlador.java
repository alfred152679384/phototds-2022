package um.tds.phototds.controlador;

import java.awt.Frame;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import um.tds.phototds.dao.*;
import um.tds.phototds.dominio.*;
import um.tds.phototds.interfaz.PrincipalGUI;

public enum Controlador {
	INSTANCE;// Singleton

	// Constantes
	public static final DateTimeFormatter HUMAN_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	// Atributos
	private Usuario usuario;
	private FactoriaDAO factoria;

	// Contructor privado (singleton)
	private Controlador() {
		try {
			factoria = FactoriaDAO.getInstancia();
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
		List<Foto> listFoto = usuario.getFotosPrincipal();
		List<ComunicacionConGUI> listCom = new LinkedList<>();
		for (Foto f : listFoto) {
			listCom.add(new ComunicacionConGUI(f.getId(), f.getUsuario().getUsername(), f.getPath(), f.getFecha(),
					f.getMeGustas(), f.getTitulo(), f.getDescripcion()));
		}
		return listCom;
	}

	public List<ComunicacionConGUI> getFotosPerfil(String username) {
		Optional<Usuario> u = RepoUsuarios.INSTANCE.findUsuario(username);
		if (u.isEmpty())
			return Collections.emptyList();
		List<Foto> listFoto = u.get().getFotosPerfil();
		List<ComunicacionConGUI> listCom = new LinkedList<>();
		for (Foto f : listFoto) {
			listCom.add(new ComunicacionConGUI(f.getId(), f.getPath()));
		}
		return listCom;
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
		if(p.isEmpty())
			return "";
		return p.get();
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
		if (aux.isPresent() && aux.get().getPassword().equals(p)) {
			this.usuario = aux.get();
			RepoPublicaciones.INSTANCE.cargarPublicacionesUsuarios();
			return true;
		}
		// Probamos con el email
		aux = RepoUsuarios.INSTANCE.findUsuarioEmail(username);
		if (aux.isPresent() && aux.get().getPassword().equals(p)) {
			this.usuario = aux.get();
			RepoPublicaciones.INSTANCE.cargarPublicacionesUsuarios();
			return true;
		}
		return false;
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
		if(password.isPresent())
			usuario.setPassword(password.get());
		if(fotoPerfil.isPresent())
			usuario.setFotoPerfil(fotoPerfil.get());
		if(presentacion.isPresent())
			usuario.setPresentacion(presentacion.get());
		
		factoria.getUsuarioDAO().update(usuario);
	}


	public boolean addFoto(String path, String title, String desc) {
		Publicacion p = new Foto(this.usuario, title, desc, path);
		PublicacionDAO daoFoto = factoria.getPublicacionDAO();
		daoFoto.create(p);
		RepoPublicaciones.INSTANCE.addPublicacion(p);
		usuario.addPublicacion(p);
		return true;
	}
	
	public void addAlbum(String titulo, String descripcion, List<ComunicacionConGUI> comList) {
		System.out.println("AÑADO ALBUM!");
	}
	
	public boolean comprobarTituloAlbum(String tit) {
		return RepoPublicaciones.INSTANCE.comprobarTituloAlbum(tit);
	}

	public Optional<Usuario> findUsuario(String username) {
		return RepoUsuarios.INSTANCE.findUsuario(username);
	}

	public void darMeGusta(int idPublicacion) {
		RepoPublicaciones.INSTANCE.darMeGusta(idPublicacion);
	}

	public void escribirComentario(int idPubli, String coment) {
		RepoPublicaciones.INSTANCE.addComentario(idPubli, coment);
	}

	public List<ComunicacionConGUI> lookFor(String txt) {
		List<ComunicacionConGUI> r = new LinkedList<>();
		if (txt.startsWith("#")) {// TODO con albumes
			List<Publicacion> l = RepoPublicaciones.INSTANCE.lookForPublicacion(txt);
			if (l.isEmpty())
				return Collections.emptyList();
			for (Publicacion p : l) {
				Foto f = (Foto) p;
				r.add(new ComunicacionConGUI(p.getHashtagContaining(txt), f.getUsuario().getNumSeguidores(),
						p.getUsuario().getUsername()));
			}
			return r;
		}
		List<Usuario> users = RepoUsuarios.INSTANCE.lookForUser(txt);
		if (users.isEmpty())
			return Collections.emptyList();
		users.remove(this.usuario);
		for (Usuario u : users) {
			r.add(new ComunicacionConGUI(u.getFotoPerfil(), u.getNombre(), u.getUsername()));
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
