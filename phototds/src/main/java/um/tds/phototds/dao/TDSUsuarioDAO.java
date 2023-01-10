package um.tds.phototds.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import um.tds.phototds.dominio.Usuario;
import beans.Entidad;
import beans.Propiedad;

/**
 * 
 * Clase que implementa el Adaptador DAO concreto de Usuario para el tipo H2.
 * 
 */
public final class TDSUsuarioDAO implements UsuarioDAO {
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

	private ServicioPersistencia servPersistencia;
	private SimpleDateFormat dateFormat;

	public TDSUsuarioDAO() {
		try {
			servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}

	private Usuario entidadToUsuario(Entidad eUsuario, boolean mode) {
		// Recupeerar Usuarios del servidor
		String username = servPersistencia.recuperarPropiedadEntidad(eUsuario, USERNAME);
		String nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE);
		String email = servPersistencia.recuperarPropiedadEntidad(eUsuario, EMAIL);
		String password = servPersistencia.recuperarPropiedadEntidad(eUsuario, PASSWORD);
		String fechaNacimiento = servPersistencia.recuperarPropiedadEntidad(eUsuario, FECHA_NACIMIENTO);
		String fotoPerfil = servPersistencia.recuperarPropiedadEntidad(eUsuario, FOTO_PERFIL);
		String presentacion = servPersistencia.recuperarPropiedadEntidad(eUsuario, PRESENTACION);
		String isPremium = servPersistencia.recuperarPropiedadEntidad(eUsuario, PREMIUM);
		// Publicaciones no se guarda aquí porque se hace ya en RepoPublicaciones
		String listaSeguidores = servPersistencia.recuperarPropiedadEntidad(eUsuario, SEGUIDORES);
		String listaSeguidos = servPersistencia.recuperarPropiedadEntidad(eUsuario, SEGUIDOS);

		List<Usuario> seguidos = new LinkedList<>();
		if (!mode)
			seguidos.addAll(cargarUsuariosSeguidos(listaSeguidos));

		List<Usuario> seguidores = new LinkedList<>();
		if (!mode)
			seguidores.addAll(cargarUsuariosSeguidores(listaSeguidores));

		Usuario usuario = new Usuario(username, nombre, email, password, fechaNacimiento, fotoPerfil, presentacion,
				isPremium, listaSeguidores, listaSeguidos);
		usuario.setSeguidoresDAO(seguidores);
		usuario.setSeguidosDAO(seguidos);
		usuario.setId(eUsuario.getId());

		return usuario;
	}

	private List<Usuario> cargarUsuariosSeguidores(String listaSeguidores) {
		if (listaSeguidores.equals("[]"))
			return Collections.emptyList();
		List<Usuario> seguidores = new LinkedList<>();
		String aux = listaSeguidores.substring(1, listaSeguidores.length() - 1);
		String[] users = aux.split(",");
		for (String s : users) {
			Entidad eUsuario = servPersistencia.recuperarEntidad(Integer.parseInt(s));
			seguidores.add(entidadToUsuario(eUsuario, true));
		}
		return seguidores;
	}

	private List<Usuario> cargarUsuariosSeguidos(String listaSeguidos) {
		if (listaSeguidos.equals("[]"))
			return Collections.emptyList();
		List<Usuario> seguidos = new LinkedList<>();
		String aux = listaSeguidos.substring(1, listaSeguidos.length() - 1);
		String[] users = aux.split(",");
		for (String s : users) {
			Entidad eUsuario = servPersistencia.recuperarEntidad(Integer.parseInt(s));
			seguidos.add(entidadToUsuario(eUsuario, true));
		}
		return seguidos;
	}

	private Entidad usuarioToEntidad(Usuario usuario) {
		Entidad eUsuario = new Entidad();
		eUsuario.setNombre(USUARIO);

		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad(USERNAME, usuario.getUsername()),
				new Propiedad(NOMBRE, usuario.getNombre()), new Propiedad(EMAIL, usuario.getEmail()),
				new Propiedad(PASSWORD, usuario.getPassword()),
				new Propiedad(FECHA_NACIMIENTO, usuario.getFechaNacimientoDAO()),
				new Propiedad(FOTO_PERFIL, usuario.getFotoPerfilDAO()),
				new Propiedad(PRESENTACION, usuario.getDAOPresentacion()),
				new Propiedad(PREMIUM, usuario.isPremiumDAO()), new Propiedad(SEGUIDORES, usuario.getSeguidoresDAO()),
				new Propiedad(SEGUIDOS, usuario.getSeguidosDAO()))));
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
				prop.setValor(usuario.isPremiumDAO());
			} else if (prop.getNombre().equals(FOTO_PERFIL)) {
				prop.setValor(usuario.getFotoPerfilDAO());
			} else if (prop.getNombre().equals(PRESENTACION)) {
				prop.setValor(usuario.getDAOPresentacion());
			} else if (prop.getNombre().equals(SEGUIDORES)) {
				prop.setValor(usuario.getSeguidoresDAO());
			} else if (prop.getNombre().equals(SEGUIDOS)) {
				prop.setValor(usuario.getSeguidosDAO());
			}
			servPersistencia.modificarPropiedad(prop);
		}
	}

	public Usuario get(int id) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(id);
		return entidadToUsuario(eUsuario, false);
	}

	public List<Usuario> getAll() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades(USUARIO);

		List<Usuario> usuarios = new LinkedList<Usuario>();
		for (Entidad eUsuario : entidades) {
			usuarios.add(get(eUsuario.getId()));
		}

		return usuarios;
	}

}
