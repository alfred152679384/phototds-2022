package um.tds.phototds.dominio;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import um.tds.phototds.controlador.Controlador;

public class Comentario {
	// Constantes
	private static final String LISTA_VACIA = "[]";

	// Atributos
	private Usuario autor;
	private String texto;

	// Constructor
	public Comentario(Usuario autor, String texto) {
		this.autor = autor;
		this.texto = texto;
	}

	// getters-setters
	public Usuario getAutor() {
		return autor;
	}

	public String getTexto() {
		return texto;
	}

	// Funcionalidad
	public static List<Comentario> comentariosToList(List<Usuario> users, String comentarios) {
		if (comentarios.equals(LISTA_VACIA))
			return Collections.emptyList();
		List<Comentario> list = new LinkedList<>();
		String aux = comentarios.substring(1, comentarios.length() - 1);
		String[] l = aux.split(",");
		int index;
		for (int i = 0; i < l.length; i++) {
			index = l[i].indexOf(";");
			String autorString = l[i].substring(0, index);
			String texto = l[i].substring(index+1);
			Optional<Usuario> autor = users.stream()
				.filter(u -> u.getUsername().equals(autorString))
				.findFirst();
			if(autor.isPresent()) {
				list.add(new Comentario(autor.get(), texto));
			}
		}
		return list;
	}

	public static String comentariosToString(List<Comentario> list) {
		String s = "[";
		for (int i = 0; i < list.size(); i++) {
			if (i == 0)
				s += list.get(i).getAutor().getUsername()+ ";" + list.get(i).getTexto();
			else
				s += "," + list.get(i).getAutor().getUsername() + ";" + list.get(i).getTexto();
		}
		s += "]";
		return s;
	}

}
