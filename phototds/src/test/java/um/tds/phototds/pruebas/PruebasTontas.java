package um.tds.phototds.pruebas;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import um.tds.phototds.controlador.ComunicacionConGUI;
import um.tds.phototds.dominio.Album;
import um.tds.phototds.dominio.Foto;
import um.tds.phototds.dominio.Usuario;

public class PruebasTontas {

	public static void main(String[] args) {
		List<ComunicacionConGUI> c = new LinkedList<>();
		c.add(new ComunicacionConGUI(0, "a", "foto1", LocalDateTime.now(), 0, "", "" ));
		c.add(new ComunicacionConGUI(0, "a", "foto2", LocalDateTime.now(), 0, "", "" ));
		c.add(new ComunicacionConGUI(0, "a", "foto3", LocalDateTime.now(), 0, "", "" ));
		
		Album a = new Album(
				new Usuario("a", "a", "a", "a", "1111-11-11",Optional.empty(), Optional.empty()),
				"album1", "5", c);
		
		a.setId(5);
		for(Foto f : a.getListaFotos()) {
			f.setId(9);
		}
		
		for(Foto f : a.getListaFotos()) {
			System.out.println(f.getId());
		}
	}

}
