package um.tds.phototds.dominio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import um.tds.phototds.controlador.Controlador;

public class GeneradorExcel {
	// atributos
	private String excelPath;
	private XSSFWorkbook wb;
	private OutputStream fileout;

	// Constructor
	public GeneradorExcel() {
		wb = new XSSFWorkbook();
		String user = Controlador.INSTANCE.getUsuarioActual().getUsername();
		excelPath = System.getProperty("user.home") + "/Documents/seguidores_" + user + ".xlsx";
		try {
			fileout = new FileOutputStream(this.excelPath);
		} catch (FileNotFoundException e) {
			System.err.println("Fichero de salida incorrecto");
			e.printStackTrace();
		}
	}

	public void generarExcel(List<Usuario> seguidores) {
		XSSFSheet sheet = wb.createSheet("Seguidores");

		// Creamos la cabecera de la tabla
		XSSFRow cabecera = sheet.createRow(0);
		cabecera.createCell(0).setCellValue("Nombre");
		cabecera.createCell(1).setCellValue("Email");
		cabecera.createCell(2).setCellValue("Presentacion");

		// Ponemos los datos de los seguidores
		for (int i = 0; i < seguidores.size(); i++) {
			XSSFRow dataRow = sheet.createRow(i+1);
			
			dataRow.createCell(0).setCellValue(seguidores.get(i).getNombre());
			dataRow.createCell(1).setCellValue(seguidores.get(i).getEmail());
			Optional<String> pres = seguidores.get(i).getPresentacion();
			if (pres.isPresent()) {
				dataRow.createCell(2).setCellValue(pres.get());
			}
		}
		
		// Guardamos
		try {
			wb.write(fileout);
			fileout.close();
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
