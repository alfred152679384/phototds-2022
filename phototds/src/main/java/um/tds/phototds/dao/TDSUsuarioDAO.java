package um.tds.phototds.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import um.tds.phototds.dominio.Notificacion;
import um.tds.phototds.dominio.Usuario;
import beans.Entidad;
import beans.Propiedad;

/**
 * 
 * Clase que implementa el Adaptador DAO concreto de Usuario para el tipo H2.
 * 
 */
public final class TDSUsuarioDAO implements UsuarioDAO {
	// Constants
	private static final String USUARIO = "Usuario";
	private static final String USERNAME = "username";
	private static final String NOMBRE = "nombre";
	private static final String EMAIL = "email";
	private static final String PASSWORD = "password";
	private static final String FECHA_NACIMIENTO = "fechaNacimiento";
	private static final String FOTO_PERFIL = "fotoPerfil";
	private static final String PRESENTACION = "presentacion";
	private static final String PREMIUM = "isPremium";
	private static final String SEGUIDORES = "listaSeguidores";
	private static final String SEGUIDOS = "listaSeguidos";
	private static final String NOTIFICACIONES = "listaNotificaciones";

	private static final String LISTA_VACIA = "[]";

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private ServicioPersistencia servPersistencia;

	public TDSUsuarioDAO() {
		try {
			servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Usuario entidadToUsuario(Entidad eUsuario) {
		// Recupeerar Usuarios del servidor
		String username = servPersistencia.recuperarPropiedadEntidad(eUsuario, USERNAME);
		String nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE);
		String email = servPersistencia.recuperarPropiedadEntidad(eUsuario, EMAIL);
		String password = servPersistencia.recuperarPropiedadEntidad(eUsuario, PASSWORD);

		// parse fecha nacimiento
		String fechaNacimientoString = servPersistencia.recuperarPropiedadEntidad(eUsuario, FECHA_NACIMIENTO);
		Date fechaNacimiento = null;
		try {
			fechaNacimiento = dateFormat.parse(fechaNacimientoString);
		} catch (ParseException e) {
			System.err.println("Fallo parser fecha nacimiento usuario " + username);
		}

		// Foto perfil
		String fotoPerfil = servPersistencia.recuperarPropiedadEntidad(eUsuario, FOTO_PERFIL);

		// Presentacion
		String presentacionString = servPersistencia.recuperarPropiedadEntidad(eUsuario, PRESENTACION);
		Optional<String> presentacion;
		presentacion = presentacionString.equals("null") ? Optional.empty() : Optional.of(presentacionString);

		// Premium
		String isPremium = servPersistencia.recuperarPropiedadEntidad(eUsuario, PREMIUM);
		boolean premium = isPremium.equals("false") ? false : true;

		// Listas de objetos no construidos
		String listaSeguidoresString = servPersistencia.recuperarPropiedadEntidad(eUsuario, SEGUIDORES);
		Optional<String> listaSeguidores;
		listaSeguidores = listaSeguidoresString.equals(LISTA_VACIA) ? Optional.empty()
				: Optional.of(listaSeguidoresString);
		String listaSeguidosString = servPersistencia.recuperarPropiedadEntidad(eUsuario, SEGUIDOS);
		Optional<String> listaSeguidos;
		listaSeguidos = listaSeguidosString.equals(LISTA_VACIA) ? Optional.empty() : Optional.of(listaSeguidosString);
		String notificacionesString = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOTIFICACIONES);
		Optional<String> notificaciones;
		notificaciones = notificacionesString.equals(LISTA_VACIA) ? Optional.empty()
				: Optional.of(notificacionesString);

		Usuario usuario = new Usuario(username, nombre, email, password, fechaNacimiento, fotoPerfil, presentacion,
				premium, listaSeguidores, listaSeguidos, notificaciones);
		usuario.setId(eUsuario.getId());

		return usuario;
	}

	private void cargarUsuariosSiguiendo(List<Usuario> users) {
		for (Usuario u : users) {
			// Cargamos usuarios seguidores
			Optional<String> listaSeguidores = u.getListaSeguidoresDAO();
			if (listaSeguidores.isPresent()) {
				String aux = listaSeguidores.get().substring(1, listaSeguidores.get().length() - 1);
				String[] usersString = aux.split(",");
				for (String s : usersString) {
					List<Usuario> usuariosSeguidores = users.stream()
							.filter(us -> us.getId() == Integer.parseInt(s))
							.collect(Collectors.toList());
					u.setSeguidoresDAO(usuariosSeguidores);
				}
			}
			
			// Cargamos usuarios seguidos
			Optional<String> listaSeguidos = u.getListaSeguidosDAO();
			if(listaSeguidos.isPresent()) {
				String aux = listaSeguidos.get().substring(1, listaSeguidos.get().length() - 1);
				String[] usersString = aux.split(",");
				for (String s : usersString) {
					List<Usuario> usuariosSeguidos = users.stream()
							.filter(us -> us.getId() == Integer.parseInt(s))
							.collect(Collectors.toList());
					u.setSeguidosDAO(usuariosSeguidos);
				}
			}
		}
	}

	private Entidad usuarioToEntidad(Usuario usuario) {
		Entidad eUsuario = new Entidad();
		eUsuario.setNombre(USUARIO);

		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(USERNAME, usuario.getUsername()),
				new Propiedad(NOMBRE, usuario.getNombre()), 
				new Propiedad(EMAIL, usuario.getEmail()),
				new Propiedad(PASSWORD, usuario.getPassword()),
				new Propiedad(FECHA_NACIMIENTO, dateFormat.format(usuario.getFechaNacimiento())),
				new Propiedad(FOTO_PERFIL, usuario.getFotoPerfil()),
				new Propiedad(PRESENTACION, presentacionToString(usuario.getPresentacion())),
				new Propiedad(PREMIUM, premiumToString(usuario.isPremium())),
				new Propiedad(NOTIFICACIONES, listNotificacionToString(usuario.getNotificaciones())),
				new Propiedad(SEGUIDORES, listUsuarioToString(usuario.getSeguidores())),
				new Propiedad(SEGUIDOS, listUsuarioToString(usuario.getSeguidos())))));
		return eUsuario;
	}
	
	public void create(Usuario usuario) {
		Entidad eUsuario = this.usuarioToEntidad(usuario);
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		usuario.setId(eUsuario.getId());
	}

	public boolean delete(Usuario usuario) {
		Entidad eUsuario;
		eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		return servPersistencia.borrarEntidad(eUsuario);
	}

	/**
	 * Permite que un Usuario modifique su perfil: password, presentacion,
	 * fotoPerfil
	 */
	public void update(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());

		for (Propiedad prop : eUsuario.getPropiedades()) {
			if (prop.getNombre().equals(PASSWORD)) {
				prop.setValor(usuario.getPassword());
			} else if (prop.getNombre().equals(PREMIUM)) {
				prop.setValor(premiumToString(usuario.isPremium()));
			} else if (prop.getNombre().equals(FOTO_PERFIL)) {
				prop.setValor(usuario.getFotoPerfil());
			} else if (prop.getNombre().equals(PRESENTACION)) {
				prop.setValor(presentacionToString(usuario.getPresentacion()));
			} else if (prop.getNombre().equals(SEGUIDORES)) {
				prop.setValor(listUsuarioToString(usuario.getSeguidores()));
			} else if (prop.getNombre().equals(SEGUIDOS)) {
				prop.setValor(listUsuarioToString(usuario.getSeguidos()));
			} else if (prop.getNombre().equals(NOTIFICACIONES)) {
				prop.setValor(listNotificacionToString(usuario.getNotificaciones()));
			}
			servPersistencia.modificarPropiedad(prop);
		}
	}

	public Usuario get(int id) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(id);
		return entidadToUsuario(eUsuario);
	}

	public List<Usuario> getAll() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades(USUARIO);

		List<Usuario> usuarios = new LinkedList<Usuario>();
		for (Entidad eUsuario : entidades) {
			usuarios.add(get(eUsuario.getId()));

		}
		cargarUsuariosSiguiendo(usuarios);

		return usuarios;
	}
	
	private String premiumToString (boolean premium) {
		return premium == false ? "false" : "true";
	}
	
	private String presentacionToString(Optional<String> presentacion) {
		return presentacion.isEmpty() ? "null" : presentacion.get();
	}
	
	private String listNotificacionToString(List<Notificacion> notificaciones) {
		String n = "[";
		for (int i = 0; i < notificaciones.size(); i++) {
			if (i == 0)
				n += notificaciones.get(i).getPublicacion().getId();
			else
				n += "," + notificaciones.get(i).getPublicacion().getId();
		}
		n += "]";
		return n;
	}
	
	private String listUsuarioToString(List<Usuario> list) {
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
}

