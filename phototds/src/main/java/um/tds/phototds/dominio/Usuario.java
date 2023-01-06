package um.tds.phototds.dominio;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import um.tds.phototds.controlador.Controlador;

public class Usuario {
	// Constantes
	private static final String DEFAULT_FOTO = "resources\\unnamed_photo.png";
	private static final String LISTA_VACIA = "[]";

	// Atributos
	private int id;
	private String username;
	private String nombre;
	private String email;
	private String password;
	private String fechN;
	private Optional<String> fotoPerfil;
	private Optional<String> presentacion;
	private boolean isPremium;
	private LinkedList<Publicacion> publicaciones;
	private LinkedList<Usuario> seguidores;
	private LinkedList<Usuario> seguidos;
	private Optional<String> seguidoresString;
	private Optional<String> seguidosString;

	// Constructor básico para nuevo usuario
	public Usuario(String username, String nombre, String email, String cont, String fechN, Optional<String> fotoPerfil,
			Optional<String> presentacion) {
		super();
		this.username = username;
		this.nombre = nombre;
		this.email = email;
		this.password = cont;
		this.fechN = fechN;
		this.fotoPerfil = fotoPerfil;
		this.presentacion = presentacion;
		this.isPremium = false;
		this.publicaciones = new LinkedList<Publicacion>();
		this.seguidores = new LinkedList<Usuario>();
		this.seguidos = new LinkedList<Usuario>();
		this.seguidoresString = Optional.empty();
		this.seguidosString = Optional.empty();
	}

	// Constructor DAO
	public Usuario(String username, String nombre, String email, String password, String fechN, String fotoPerfil,
			String presentacion, String isPremium, String seguidores, String seguidos) {
		this.username = username;
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		this.fechN = fechN;
		this.fotoPerfil = fotoPerfil.equals("null") ? Optional.empty() : Optional.of(fotoPerfil);
		this.presentacion = presentacion.equals("null") ? Optional.empty() : Optional.of(presentacion);
		this.isPremium = isPremium.equals("true") ? true : false;
		this.publicaciones = new LinkedList<>(); // No se inicializa porque todavía no tienen por qué existir las fotos
		this.seguidores = new LinkedList<Usuario>();
		this.seguidoresString = Optional.of(seguidores);
		this.seguidos = new LinkedList<Usuario>();
		this.seguidosString = Optional.of(seguidos);
	}

	// getters-setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public String getEmail() {
		return this.email;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getNumSeguidores() {
		return this.seguidores.size();
	}

	public int getNumSeguidos() {
		return this.seguidos.size();
	}

	public String getFechaNacimiento() {
		return this.fechN;
	}

	public String getFotoPerfil() {
		if (fotoPerfil.isPresent())
			return fotoPerfil.get();
		return DEFAULT_FOTO;
	}

	public String getFotoPerfilDAO() {
		if (fotoPerfil.isPresent())
			return fotoPerfil.get();
		return "null";
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = Optional.of(fotoPerfil);
	}

	public Optional<String> getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = Optional.of(presentacion);
	}

	public String getDAOPresentacion() {
		if (presentacion.isPresent())
			return presentacion.get();
		return "null";
	}

	public String isPremiumDAO() {
		if (isPremium)
			return "true";
		return "false";
	}

	public String getSeguidoresDAO() {
		return listToString(this.seguidores);
	}

	public String getSeguidosDAO() {
		return listToString(this.seguidos);
	}

	public int getNumPublicaciones() {
		return this.publicaciones.size();
	}

	// Funcionalidad
	public void addPublicacion(Publicacion p) {
		this.publicaciones.add(p);
	}

	public void darMeGusta(Publicacion f) {
		publicaciones.stream().filter(p -> p.getId() == f.getId()).map(p -> p.darMeGusta());
	}

	public void addComentario(Publicacion f, Comentario c) {
		publicaciones.stream().filter(p -> p.getId() == f.getId()).map(p -> p.addComentario(c));
	}

	public List<Foto> getFotosPrincipal() {
		// Devuelvo mis fotos y las de la gente que sigo
		List<Foto> fotos = publicaciones.stream().filter(p -> p instanceof Foto).map(p -> (Foto) p)
				.collect(Collectors.toList());

		fotos.addAll(seguidos.stream().flatMap(u -> u.getFotosPerfil().stream()).collect(Collectors.toList()));

		fotos.sort(null);// Ordena por el comparable
		if (fotos.size() > 20)
			return fotos.subList(0, 20);
		else
			return fotos;
	}

	public List<Foto> getFotosPerfil() {
		return publicaciones.stream().filter(p -> p instanceof Foto).map(p -> (Foto) p).collect(Collectors.toList());
	}

	private String listToString(LinkedList<Usuario> list) {
		String s = "[";
		for (int i = 0; i < list.size(); i++) {
			if (i == 0)
				s += list.get(i).getUsername();
			else
				s += "," + list.get(i).getUsername();
		}
		s += "]";
		return s;
	}

	public void cargarPublicaciones(List<Publicacion> p) {
		if (p.isEmpty())
			this.publicaciones = new LinkedList<>();
		else
			this.publicaciones.addAll(p);
	}

	/**
	 * Cuando se cargan los objetos Usuario no podemos crear las listas de
	 * seguidores y seguidos porque puede que algunos usuarios todavía no estén
	 * creados, por lo que los creamos cuando se llama a esta función
	 */
	public void cargarListasUsuarios() {
		cargarSeguidores();
		cargarSeguidos();
	}

	private void cargarSeguidores() {
		if (this.seguidoresString.get().equals(LISTA_VACIA))
			return;
		String n = seguidoresString.get().substring(1, seguidoresString.get().length() - 1);
		String[] s = n.split(",");
		for (int i = 0; i < s.length; i++) {
			Optional<Usuario> u = Controlador.INSTANCE.findUsuario(s[i]);
			if (u.isPresent()) {
				this.seguidores.add(u.get());
			} else {
				System.err.println("Usuario " + u.get().getUsername() + ": No existe");
			}
		}
	}

	private void cargarSeguidos() {
		if (this.seguidosString.get().equals(LISTA_VACIA))
			return;
		String n = seguidosString.get().substring(1, seguidosString.get().length() - 1);
		String[] s = n.split(",");
		for (int i = 0; i < s.length; i++) {
			Optional<Usuario> u = Controlador.INSTANCE.findUsuario(s[i]);
			if (u.isPresent()) {
				this.seguidores.add(u.get());
			} else {
				System.err.println("Usuario " + u.get().getUsername() + ": No existe");
			}
		}
	}

}
