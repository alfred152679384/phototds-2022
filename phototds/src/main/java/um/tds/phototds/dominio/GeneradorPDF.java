package um.tds.phototds.dominio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import um.tds.phototds.controlador.Controlador;

public class GeneradorPDF {
	private PdfDocument pdf;
	private Document doc;
	
	public GeneradorPDF() {
		String username = Controlador.INSTANCE.getUsuarioActual();
		String path = System.getProperty("user.home") + "/Documents/seguidores_" + username + ".pdf";
		try {
			this.pdf = new PdfDocument(new PdfWriter(path));
			this.doc = new Document(pdf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void generarPDF(List<Usuario> seguidores) {
		Table table = new Table(3);
		
		table.addCell(new Cell().add(new Paragraph("Nombre")));
		table.addCell(new Cell().add(new Paragraph("Email")));
		table.addCell(new Cell().add(new Paragraph("Presentacion")));
		
		//Añadimos los elementos a la tabla
		for(Usuario u : seguidores) {
			table.addCell(new Cell().add(new Paragraph(u.getNombre())));
			table.addCell(new Cell().add(new Paragraph(u.getEmail())));
			Optional<String> pres = u.getPresentacion();
			if(pres.isPresent())
				table.addCell(new Cell().add(new Paragraph(pres.get())));
			else table.addCell(new Cell().add(new Paragraph()));
		}
		
		doc.add(table);
		doc.close();
		
		
	}
}
