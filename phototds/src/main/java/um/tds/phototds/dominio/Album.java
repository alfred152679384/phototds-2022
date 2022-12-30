package um.tds.phototds.dominio;

import java.time.LocalDate;
import java.util.LinkedList;

public class Album extends Publicacion{
	//Atributos
	private LinkedList<Foto> listaFotos;

	//Constructor DAO TODO comprobar si pasa un usuario o un string el señor dao
		public Album(String user, String titulo, String fecha, String descripcion, 
				String meGustas, String hashtags, String comentarios, String path) {
			super(user,titulo, fecha, descripcion, 
					meGustas, hashtags, comentarios);
			//this.listaFotos = listaFotos; TODO
		}

		
		//Constructor básico
		public Album(Usuario user, String titulo, String descripcion, String listFotos) {
			super(user, titulo, descripcion);
			//this.listaFotos = listaFotos; TODO
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
