package umu.tds.fotos;

public class CargadorFotos {
	public static Fotos cargadorFotos(String ruta) {
		return MapperFotosXMLtoJava.cargarFotos(ruta);
	}
}
