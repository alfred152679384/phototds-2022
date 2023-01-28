package um.tds.phototds.dominio;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class Album extends Publicacion {
	// Atributos
	private LinkedList<Foto> listaFotos;

	// Constructor DAO
	public Album(String user, String titulo, String fecha, String descripcion, String meGustas, String hashtags,
			String comentarios, String listaFotos) {
		super(user, titulo, fecha, descripcion, meGustas, hashtags, comentarios);
		this.listaFotos = new LinkedList<>();
	}

	// Constructor básico
	public Album(Usuario user, String titulo, String descripcion, List<Foto> fList) {
		super(user, titulo, descripcion);
		this.listaFotos = new LinkedList<>(fList);
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
