package um.tds.phototds.dominio;

import java.time.LocalDate;
import java.util.LinkedList;

public class Album extends Publicacion{
	//Atributos
	private LinkedList<Foto> listaFotos;

	//Constructor DAO
		public Album(String titulo, String fecha, String descripcion, String meGustas, String comentarios, String listaFotos) {
			super(titulo, fecha, descripcion, meGustas, comentarios);
			//this.listaFotos = listaFotos; //Cargar fotos a la lista
		}

		
		//Constructor básico
		public Album(String titulo, String fecha, String descripcion, String path) {
			super(titulo, fecha, descripcion);
			//this.listaFotos = listaFotos;
		}

	//getters-setters
	public LinkedList<Foto> getListaFotos() {
		return listaFotos;
	}

	public String getListaFotosString() {
		return listaFotos.toString();
	}
	
	//Funcionalidad
	
	public void addFoto(Foto foto) {
		listaFotos.add(foto);
	}
	
	
}
