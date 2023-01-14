package um.tds.phototds.controlador;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ComunicacionConGUI {
	//Constantes
	public static final int MODE_BUSQ_USUARIOS = 0;
	public static final int MODE_BUSQ_HASHTAGS = 1;
//	public static final int MODE_PROFILE_FOTO = 2;
//	public static final int MODE_ENTRADA_PRINCIPAL = 3;
//	public static final int MODE_ALBUM = 4;
	
	//Atributos
	private int mode;
	private String username;
	private String pathFoto;
	private String nombreUsuario;
	private String hashtag;
	private int numSeguidoresUsuario;
	private int idPubli;
	private LocalDateTime fecha;
	private int meGustas;
	private String titulo;
	private String desc;
	private List<ComunicacionConGUI> fotosAlbumes;
	
	
	public ComunicacionConGUI() {
		this.fotosAlbumes = new LinkedList<>();
	}
	
	//Getters & Setters
	public int getNumSeguidoresUsuario() {
		return numSeguidoresUsuario;
	}

	public void setNumSeguidoresUsuario(int numSeguidoresUsuario) {
		this.numSeguidoresUsuario = numSeguidoresUsuario;
	}

	public int getIdPubli() {
		return idPubli;
	}

	public void setIdPubli(int idPubli) {
		this.idPubli = idPubli;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<ComunicacionConGUI> getFotosAlbumes() {
		return fotosAlbumes;
	}

	public void setFotosAlbumes(List<ComunicacionConGUI> fotosAlbumes) {
		this.fotosAlbumes = fotosAlbumes;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getMode() {
		return this.mode;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPathFoto(String pathFoto) {
		this.pathFoto = pathFoto;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public void setMeGustas(int meGustas) {
		this.meGustas = meGustas;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getPathFoto() {
		return this.pathFoto;
	}
	
	public String getNombreUsuario() {
		return this.nombreUsuario;
	}
	
	public String getHashtag() {
		return this.hashtag;
	}
	
	public int getSeguidoresUsuario() {
		return this.numSeguidoresUsuario;
	}
	
	public int getIdPublicacion() {
		return this.idPubli;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public LocalDateTime getFecha() {
		return this.fecha;
	}
	
	public int getMeGustas() {
		return this.meGustas;
	}
	
	public String getTitulo() {
		return this.titulo;
	}
	
	public String getDescripcion() {
		return this.desc;
	}
	
	public List<ComunicacionConGUI> getListaFotosAlbum() {
		return Collections.unmodifiableList(this.fotosAlbumes);
	}
	
	//Funcionalidad
	public void addFotos(List<ComunicacionConGUI> comList) {
		this.fotosAlbumes.addAll(comList);
	}

	@Override
	public String toString() {
		String af = "[";
		for(ComunicacionConGUI c : fotosAlbumes) {
			af+="id="+c.getIdPubli()+"_path="+c.getPathFoto()+",";
		}
		af+="]";
		return "ComunicacionConGUI [mode=" + mode + ", username=" + username + ", pathFoto=" + pathFoto
				+ ", nombreUsuario=" + nombreUsuario + ", hashtag=" + hashtag + ", numSeguidoresUsuario="
				+ numSeguidoresUsuario + ", idPubli=" + idPubli + ", fecha=" + fecha + ", meGustas=" + meGustas
				+ ", titulo=" + titulo + ", desc=" + desc + ", fotosAlbumes=" + af +"]";
	}
	
	
}
