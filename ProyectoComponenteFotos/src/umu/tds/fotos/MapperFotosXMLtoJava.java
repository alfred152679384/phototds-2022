package umu.tds.fotos;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class MapperFotosXMLtoJava {

	public static Fotos cargarFotos(String fichero) {

		JAXBContext jc;
		Fotos fotos = null;
		try {
			jc = JAXBContext.newInstance("umu.tds.fotos");
			Unmarshaller u = jc.createUnmarshaller();
			File file = new File(fichero);
			fotos = (Fotos) u.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}	
		return fotos;
	}
}
