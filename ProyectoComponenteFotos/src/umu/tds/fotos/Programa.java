package umu.tds.fotos;

public class Programa {

	public static void main(String[] args) {

		Fotos fotos = MapperFotosXMLtoJava
				.cargarFotos("xml/fotos.xml"); //Obtener fichero a cargar mediante JFileChooser en Swing
	
		for (Foto foto: fotos.getFotos()) {
			System.out.println("Titulo: " + foto.getTitulo());
			System.out.println(" Descripcion: " + foto.getDescripcion());
			System.out.println(" Path : " + foto.getPath());
			System.out.println(" HashTags : ");
			foto.getHashTags().stream()
								.flatMap(h -> h.getHashTag().stream())
								.forEach(h -> System.out.println("   " + h + " "));

			System.out.println("***** ***** *****");
			
			
		}
	
	}

}
