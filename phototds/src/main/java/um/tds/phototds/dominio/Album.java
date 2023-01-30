package um.tds.phototds.dominio;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class Album extends Publicacion {
	// Atributos
	private List<Foto> listaFotos;
	private Optional<String> fotosDAO;
	
	// Constructor básico
	public Album(Usuario user, String titulo, String descripcion, List<Foto> fList) {
		super(user, titulo, descripcion);
		this.listaFotos = new LinkedList<>(fList);
		this.fotosDAO = Optional.empty();
	}

	// Constructor DAO
	public Album(String userDAO, String titulo, LocalDateTime fecha, String descripcion, 
			int meGustas, List<String> hashtags, String comentariosDAO, String fotos) {
		super(userDAO, titulo, fecha, descripcion, meGustas, hashtags, comentariosDAO);
		this.listaFotos = new LinkedList<>();
	}

	// getters-setters
	public List<Foto> getListaFotos() {
		return this.listaFotos.stream().sorted().collect(Collectors.toList());
	}

	// Funcionalidad
	public boolean addFotoDAO(Foto f) {
		listaFotos.add(f);
		return true;
	}
	
	public void addFoto(Foto f) {
		listaFotos.add(f);
	}
	
	@Override
	public void darMeGusta() {
		this.meGustas++;
		listaFotos.stream().forEach(f -> f.darMeGusta());
	}
}
