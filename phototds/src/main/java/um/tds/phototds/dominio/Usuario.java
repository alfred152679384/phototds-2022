package um.tds.phototds.dominio;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.Comparator;

import um.tds.phototds.controlador.Controlador;

public class Usuario implements NotificacionListener {
	// Constantes
	private static final String DEFAULT_FOTO = "resources\\unnamed_photo.png";
	private static final int PRECIO_PREMIUM = 5;// 5€

	// Atributos
	private int id;
	private String username;
	private String nombre;
	private String email;
	private String password;

	private Date fechaNacimiento;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private Descuento descuento;

	private Optional<String> fotoPerfil;
	private Optional<String> presentacion;
	private boolean isPremium;
	private LinkedList<Publicacion> publicaciones;

	private LinkedList<Usuario> seguidores;
	private LinkedList<Usuario> seguidos;
	private Optional<String> seguidoresString;
	private Optional<String> seguidosString;

	private List<Notificacion> notificaciones;
	private String notifString;

	// Constructor básico para nuevo usuario
	public Usuario(String username, String nombre, String email, String cont, String fechN, Optional<String> fotoPerfil,
			Optional<String> presentacion) {
		super();
		this.username = username;
		this.nombre = nombre;
		this.email = email;
		this.password = cont;
		try {
			this.fechaNacimiento = dateFormat.parse(fechN);
		} catch (ParseException e) {
			System.err.println("Fallo Fecha Nacimiento Usuario");
		}
		this.fotoPerfil = fotoPerfil;
		this.presentacion = presentacion;
		this.isPremium = false;
		this.publicaciones = new LinkedList<Publicacion>();
		this.seguidores = new LinkedList<Usuario>();
		this.seguidos = new LinkedList<Usuario>();
		this.seguidoresString = Optional.empty();
		this.seguidosString = Optional.empty();
		this.notificaciones = new LinkedList<>();
	}

	// Constructor DAO
	public Usuario(String username, String nombre, String email, String password, String fechN, String fotoPerfil,
			String presentacion, String isPremium, String seguidores, String seguidos, String notificaciones) {
		this.username = username;
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		try {
			this.fechaNacimiento = this.dateFormat.parse(fechN);
		} catch (ParseException e) {
			System.err.println("Fallo Fecha Nacimiento Usuario");
		}
		this.fotoPerfil = fotoPerfil.equals("null") ? Optional.empty() : Optional.of(fotoPerfil);
		this.presentacion = presentacion.equals("null") ? Optional.empty() : Optional.of(presentacion);
		this.isPremium = isPremium.equals("true") ? true : false;
		this.publicaciones = new LinkedList<>(); // No se inicializa porque todavía no tienen por qué existir las fotos
		this.seguidores = new LinkedList<Usuario>();
		this.seguidoresString = Optional.of(seguidores);
		this.seguidos = new LinkedList<Usuario>();
		this.seguidosString = Optional.of(seguidos);
		this.notifString = notificaciones;
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

	public String getFechaNacimientoDAO() {
		return this.dateFormat.format(fechaNacimiento);
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
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

	public boolean isPremium() {
		return this.isPremium;
	}

	public void setPremium(boolean premium) {
		this.isPremium = premium;
	}

	public List<Publicacion> getPublicaciones() {
		return Collections.unmodifiableList(this.publicaciones);
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

	public String getNotificacionesDAO() {
		String n = "[";
		for (int i = 0; i < this.notificaciones.size(); i++) {
			if (i == 0)
				n += notificaciones.get(i).getPublicacion().getId();
			else
				n += "," + notificaciones.get(i).getPublicacion().getId();
		}
		n += "]";
		return n;
	}

	public String isPremiumDAO() {
		if (isPremium)
			return "true";
		return "false";
	}

	public String getSeguidoresDAO() {
		return listToString(this.seguidores);
	}

	public String getListaSeguidoresString() {
		return this.seguidoresString.get();
	}

	public String getListaSeguidosString() {
		return this.seguidosString.get();
	}

	public String getSeguidosDAO() {
		return listToString(this.seguidos);
	}

	public void setSeguidoresDAO(List<Usuario> seguidores) {
		this.seguidores = new LinkedList<>(seguidores);
	}

	public void setSeguidosDAO(List<Usuario> seguidos) {
		this.seguidos = new LinkedList<>(seguidos);
	}

	public int getNumPublicaciones() {
		return this.publicaciones.size();
	}
	
	public List<Usuario> getSeguidores(){
		return this.seguidores;
	}

	public List<Notificacion> getNotificaciones() {
		return this.notificaciones;
	}

	// Funcionalidad
	public void addPublicacion(Publicacion p) {
		this.publicaciones.add(p);
		seguidores.stream().forEach(s -> s.notificarPublicacion(new Notificacion(p, p.getFecha())));
	}

	public void darMeGusta(Publicacion f) {
		publicaciones.stream().filter(p -> p.getId() == f.getId()).map(p -> p.darMeGusta());
	}

	public void removePublicacion(int id) {
		publicaciones.removeIf(p -> p.getId() == id);
	}

	public void addComentario(Publicacion f, Comentario c) {
		publicaciones.stream().filter(p -> p.getId() == f.getId()).map(p -> p.addComentario(c));
	}

	public void seguirUsuario(Usuario user) {
		if (!seguidos.contains(user))
			this.seguidos.add(user);
	}

	public void addseguidor(Usuario user) {
		if (!seguidores.contains(user))
			this.seguidores.add(user);
	}

	public void notificarPublicacion(Notificacion n) {
		this.notificaciones.add(n);
	}

	public List<Foto> getFotosPrincipal() {
		List<Foto> fotos = publicaciones.stream().filter(p -> p instanceof Foto).map(p -> (Foto) p)
				.collect(Collectors.toList());

		seguidos.stream().flatMap(u -> u.getFotosPerfil().stream()).forEach(f -> fotos.add(f));

		return fotos.stream().sorted((o1, o2) -> o1.compareTo(o2)).limit(20).collect(Collectors.toList());
	}

	public void cargarNotificaciones() {
		this.notificaciones = new LinkedList<>();
		if (this.notifString.equals("[]")) {
			return;
		}			
		String aux = this.notifString.substring(1, notifString.length() - 1);
		String[] notifs = aux.split(",");
		for (String n : notifs) {
			Optional<Publicacion> p = Controlador.INSTANCE.findPublicacion(Integer.parseInt(n));
			notificaciones.add(new Notificacion(p.get(), p.get().getFecha()));
		}
	}

	public double aplicarDescuento(int mode) {
		switch (mode) {
		case 0:
			this.descuento = new DescuentoEdad(this);
			return PRECIO_PREMIUM * (1 - descuento.aplicarDescuento());

		case 1:
			this.descuento = new DescuentoMeGustas(this);
			return PRECIO_PREMIUM * (1 - descuento.aplicarDescuento());

		default:
		}

		return PRECIO_PREMIUM;
	}

	public List<Foto> getFotosPerfil() {
		return publicaciones.stream().filter(p -> p instanceof Foto).map(p -> (Foto) p).collect(Collectors.toList());
	}

	public List<Album> getAlbumesPerfil() {
		return publicaciones.stream().filter(p -> p instanceof Album).map(p -> (Album) p).collect(Collectors.toList());
	}

	private String listToString(LinkedList<Usuario> list) {
		String s = "[";
		for (int i = 0; i < list.size(); i++) {
			if (i == 0)
				s += Integer.toString(list.get(i).getId());
			else
				s += "," + Integer.toString(list.get(i).getId());
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
}
