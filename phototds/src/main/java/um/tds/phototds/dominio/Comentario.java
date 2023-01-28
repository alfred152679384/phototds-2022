package um.tds.phototds.dominio;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
	public static List<Comentario> comentariosToList(String s) {
		if (s.equals(LISTA_VACIA))
			return Collections.emptyList();
		List<Comentario> list = new LinkedList<>();
		String aux = s.substring(1, s.length() - 1);
		String[] l = aux.split(",");
		int index;
		String autor, texto;
		for (int i = 0; i < l.length; i++) {
			index = l[i].indexOf(";");
			autor = l[i].substring(0, index);
			texto = l[i].substring(index+1);
			list.add(new Comentario(Controlador.INSTANCE.findUsuario(autor).get(),texto));
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
