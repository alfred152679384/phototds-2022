package um.tds.phototds.dominio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import um.tds.phototds.controlador.Controlador;

public class Usuario implements NotificacionListener {
	// Constantes
	private static final String DEFAULT_FOTO = "resources\\unnamed_photo.png";

	// Atributos
	private int id;
	private String username;
	private String nombre;
	private String email;
	private String password;

	private Date fechaNacimiento;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private DescuentoEstrategia descuentoEstrategia;// Not DAO

	private String fotoPerfil;
	private Optional<String> presentacion;
	private boolean isPremium;
	private LinkedList<Publicacion> publicaciones;

	private LinkedList<Usuario> seguidores;
	private LinkedList<Usuario> seguidos;
	private List<Notificacion> notificaciones;
	private Optional<String> seguidoresDAO;
	private Optional<String> seguidosDAO;
	private Optional<String> notificacionesDAO;

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
		if(fotoPerfil.isEmpty())
			 this.fotoPerfil = DEFAULT_FOTO;
		else this.fotoPerfil = fotoPerfil.get();
		this.presentacion = presentacion;
		this.isPremium = false;

		this.publicaciones = new LinkedList<>();
		this.seguidores = new LinkedList<>();
		this.seguidos = new LinkedList<>();
		this.notificaciones = new LinkedList<>();

		this.seguidoresDAO = Optional.empty();
		this.seguidosDAO = Optional.empty();
		this.notificacionesDAO = Optional.empty();
	}

	// Constructor DAO
	public Usuario(String username, String nombre, String email, String password, Date fechN,
			String fotoPerfil, Optional<String> presentacion, boolean isPremium, 
			Optional<String> seguidores, Optional<String> seguidos, Optional<String> notificaciones) {
		this.username = username;
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		this.fechaNacimiento = fechN;

		this.fotoPerfil = fotoPerfil;
		this.presentacion = presentacion;
		this.isPremium = isPremium;

		// Todavía no se han cargado las publicaciones, seguidores, seguidos y
		// notificaciones
		this.publicaciones = new LinkedList<>();
		this.seguidores = new LinkedList<>();
		this.seguidos = new LinkedList<>();
		this.notificaciones = new LinkedList<>();

		this.seguidoresDAO = seguidores;
		this.seguidosDAO = seguidos;
		this.notificacionesDAO = notificaciones;
	}

	// Getters & Setters
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

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public String getFotoPerfil() {
		return this.fotoPerfil;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
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
	
	public int getNumPublicaciones() {
		return this.publicaciones.size();
	}

	public Optional<String> getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = Optional.of(presentacion);
	}

	public void setDescuentoEstrategia(DescuentoEstrategia d) {
		this.descuentoEstrategia = d;
	}

	public void setSeguidoresDAO(List<Usuario> seguidores) {
		this.seguidores.addAll(seguidores);
	}
	
	public Optional<String> getListaSeguidosDAO() {
		if(this.seguidosDAO.isPresent()) {
			Optional<String> aux = this.seguidosDAO;
			this.seguidosDAO = Optional.empty();
			return aux;
		}
		return this.seguidosDAO;
	}
	
	public Optional<String> getListaSeguidoresDAO() {
		if(this.seguidoresDAO.isPresent()) {
			Optional<String> aux = this.seguidoresDAO;
			this.seguidoresDAO = Optional.empty();
			return aux;
		}
		return this.seguidoresDAO;
	}

	public void setSeguidosDAO(List<Usuario> seguidos) {
		this.seguidos.addAll(seguidos);
	}

	public List<Usuario> getSeguidores() {
		return this.seguidores;
	}
	
	public List<Usuario> getSeguidos() {
		return this.seguidos;
	}

	public List<Notificacion> getNotificaciones() {
		return new LinkedList<>(this.notificaciones);
	}
	
	public void setNotificaciones() {
		this.notificaciones.clear();
	}

	public List<Foto> getTopMG() {
		return this.getFotosPerfil().stream().sorted((o1, o2) -> Integer.compare(o2.getMeGustas(), o1.getMeGustas()))
				.limit(10).collect(Collectors.toList());
	}

	// Funcionalidad
	public Foto addFoto(String title, String descripcion, String path) {
		Foto f = new Foto(this, title, descripcion, path);

		addPublicacion(f);
		return f;
	}

	public Album addAlbum(String titulo, String descripcion, List<String> fTitulo, List<String> fDesc,
			List<String> fPath) {
		// Creamos las fotos
		List<Foto> fotosAlbum = new LinkedList<>();
		for (int i = 0; i < fTitulo.size(); i++) {
			fotosAlbum.add(new Foto(this, fTitulo.get(i), fDesc.get(i), fPath.get(i)));
		}

		// Creamos el album
		Album a = new Album(this, titulo, descripcion, fotosAlbum);

		addPublicacion(a);
		return a;
	}

	private void addPublicacion(Publicacion p) {
		this.publicaciones.add(p);
		seguidores.stream().forEach(s -> s.notificarPublicacion(new Notificacion(p, p.getFecha())));
	}

	public List<Foto> addFotosToAlbum(Album a, List<String> titList, List<String> descList, List<String> pList) {
		List<Foto> nuevasFotos = new LinkedList<>();
		for (int i = 0; i < titList.size(); i++) {
			Foto f = new Foto(this, titList.get(i), descList.get(i), pList.get(i));
			a.addFoto(f);
			nuevasFotos.add(f);
		}
		return nuevasFotos;
	}

	public void darMeGusta(Publicacion f) {
		publicaciones.stream().filter(p -> p.getId() == f.getId()).forEach(p -> p.darMeGusta());
	}

	public void removePublicacion(Publicacion p) {
		boolean removed = publicaciones.remove(p);
		if (!removed)
			System.err.println("Publicacion " + p.getTitulo() + " no ha sido borrada");
	}

	public void seguirUsuario(Usuario user) {
		if (!seguidos.contains(user))
			this.seguidos.add(user);
	}

	public void addseguidor(Usuario user) {
		if (!seguidores.contains(user))
			this.seguidores.add(user);
	}

	@Override
	public void notificarPublicacion(Notificacion n) {
		this.notificaciones.add(n);
	}

	public List<Foto> getFotosPrincipal() {
		List<Foto> fotos = publicaciones.stream().filter(p -> p instanceof Foto).map(p -> (Foto) p)
				.collect(Collectors.toList());

		seguidos.stream().flatMap(u -> u.getFotosPerfil().stream()).forEach(f -> fotos.add(f));

		return fotos.stream().sorted((o1, o2) -> o1.compareTo(o2)).limit(20).collect(Collectors.toList());
	}

	public double getPrecio() {
		return Controlador.PRECIO_PREMIUM * (1 - descuentoEstrategia.aplicarDescuento());
	}

	public List<Foto> getFotosPerfil() {
		return publicaciones.stream().filter(p -> p instanceof Foto).map(p -> (Foto) p)
				.sorted((o1, o2) -> o1.compareTo(o2)).collect(Collectors.toList());
	}

	public List<Album> getAlbumesPerfil() {
		return publicaciones.stream()
				.filter(p -> p instanceof Album)
				.map(p -> (Album) p)
				.sorted()
				.collect(Collectors.toList());
	}

	public void cargarPublicaciones(List<Publicacion> p) {
		if (p.isEmpty())
			this.publicaciones = new LinkedList<>();
		else
			this.publicaciones.addAll(p);
	}
	
	public void cargarNotificaciones(List<Publicacion> publis) {
		if (notificacionesDAO.isEmpty()) {
			return;
		}
		this.notificaciones = new LinkedList<>();
		String aux = notificacionesDAO.get().substring(1, notificacionesDAO.get().length() - 1);
		String[] notifs = aux.split(",");
		for (String n : notifs) {
			Optional<Publicacion> p = publis.stream()
					.filter(pu -> pu.getId() == Integer.parseInt(n))
					.findFirst();
			if(p.isPresent()) {
				notificaciones.add(new Notificacion(p.get(), p.get().getFecha()));
			}
		}
		this.notificacionesDAO = Optional.empty();
	}
}