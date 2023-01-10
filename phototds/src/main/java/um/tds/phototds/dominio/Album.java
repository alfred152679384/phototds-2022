package um.tds.phototds.dominio;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import um.tds.phototds.controlador.ComunicacionConGUI;
import um.tds.phototds.controlador.Controlador;

public class Album extends Publicacion {
	// Atributos
	private LinkedList<Foto> listaFotos;
	private String fotosString;

	// Constructor DAO
	public Album(String user, String titulo, String fecha, String descripcion, String meGustas, String hashtags,
			String comentarios, String listaFotos) {
		super(user, titulo, fecha, descripcion, meGustas, hashtags, comentarios);
		this.listaFotos = new LinkedList<>();
		this.fotosString = listaFotos;
	}

	// Constructor básico
	public Album(Usuario user, String titulo, String descripcion, List<ComunicacionConGUI> comList) {
		super(user, titulo, descripcion);
		this.listaFotos = new LinkedList<>();
		for (ComunicacionConGUI c : comList) {
			this.listaFotos.add(new Foto(user, c.getTitulo(), c.getDescripcion(), c.getPathFoto()));
		}
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
	
	public void addFotos(List<Foto> fList) {
		listaFotos.addAll(fList);
	}
	
}
