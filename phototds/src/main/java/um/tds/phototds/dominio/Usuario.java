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
	private DescuentoEstrategia descuentoEstrategia;//Not DAO

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
		this.notifString = "[]";
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
	
	public void setDescuentoEstrategia(DescuentoEstrategia d) {
		this.descuentoEstrategia = d;
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
		this.seguidores.addAll(seguidores);
	}

	public void setSeguidosDAO(List<Usuario> seguidos) {
		this.seguidos.addAll(seguidos);
	}

	public int getNumPublicaciones() {
		return this.publicaciones.size();
	}

	public List<Usuario> getSeguidores() {
		return this.seguidores;
	}

	public List<Notificacion> getNotificaciones() {
		List<Notificacion> notifs = new LinkedList<>(this.notificaciones);
		this.notificaciones.clear();
		return notifs;
	}
	
	public List<Foto> getTopMG(){
		return this.getFotosPerfil().stream().sorted((o1, o2) -> Integer.compare(o2.getMeGustas(), o1.getMeGustas()))
				.limit(10).collect(Collectors.toList());
	}

	// Funcionalidad
	public Foto addFoto (String title, String descripcion, String path) {
		Foto f = new Foto(this, title, descripcion, path);
		
		addPublicacion(f);
		return f;
	}
	
	public Album addAlbum (String titulo, String descripcion, List<String> fTitulo, List<String> fDesc,
			List<String> fPath) {
		//Creamos las fotos
		List<Foto> fotosAlbum = new LinkedList<>();
		for (int i = 0; i < fTitulo.size(); i++) {
			fotosAlbum.add(new Foto(this, fTitulo.get(i), fDesc.get(i), fPath.get(i)));
		}
		
		//Creamos el album
		Album a = new Album(this, titulo, descripcion, fotosAlbum);
		
		addPublicacion(a);
		return a;
	}
	
	private void addPublicacion(Publicacion p) {
		this.publicaciones.add(p);
		seguidores.stream().forEach(s -> s.notificarPublicacion(new Notificacion(p, p.getFecha())));
	}
	
	public List<Foto> addFotosToAlbum(Album a, List<String> titList, List<String> descList,
			List<String> pList) {
		List<Foto> nuevasFotos = new LinkedList<>();
		for(int i = 0; i< titList.size(); i++) {
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
		if(!removed) System.err.println("Publicacion "+p.getTitulo()+" no ha sido borrada");
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

	public double getPrecio() {
		return Controlador.PRECIO_PREMIUM * (1-descuentoEstrategia.aplicarDescuento());
	}

	public List<Foto> getFotosPerfil() {
		return publicaciones.stream().filter(p -> p instanceof Foto).map(p -> (Foto) p)
				.sorted((o1, o2) -> o1.compareTo(o2)).collect(Collectors.toList());
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