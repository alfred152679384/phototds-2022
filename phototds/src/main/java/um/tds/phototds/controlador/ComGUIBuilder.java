package um.tds.phototds.controlador;

import java.time.LocalDateTime;
import java.util.List;

public class ComGUIBuilder {
	//Atributos
	private ComunicacionConGUI c;	
	
	//Constructor
	public ComGUIBuilder() {
		this.c = new ComunicacionConGUI();
	}
	
	
	//Funcionalidad builder
	public void buildUsername(String username) {
		c.setUsername(username);
	}
	
	public void buildPathFoto(String pathFoto) {
		c.setPathFoto(pathFoto);
	}
	
	public void buildNombreUsuario(String nombreUsuario) {
		c.setNombreUsuario(nombreUsuario);
	}
	
	public void buildHashtag(String hashtag) {
		c.setHashtag(hashtag);
	}
	
	public void buildNumSeguidoresUsuario(int numSeguidores) {
		c.setNumSeguidoresUsuario(numSeguidores);
	}
	
	public void buildIdPublicacion(int idPublicacion) {
		c.setIdPubli(idPublicacion);
	}
	
	public void buildFecha(LocalDateTime fecha) {
		c.setFecha(fecha);
	}
	
	public void buildMeGustas(int meGustas) {
		c.setMeGustas(meGustas);
	}
	
	public void buildTitulo(String titulo) {
		c.setTitulo(titulo);
	}
	
	public void buildMode(int mode) {
		c.setMode(mode);
	}
	
	public void buildDescripcion(String descripcion) {
		c.setDesc(descripcion);
	}
	
	public void buildFotosAlbumes(List<ComunicacionConGUI> fotosAlbumes) {
		c.setFotosAlbumes(fotosAlbumes);
	}
	
	//Funcionalidad Builder
	public ComunicacionConGUI getResult() {
		return this.c;
	}
}
