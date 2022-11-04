package um.tds.phototds.launcher;

import java.awt.EventQueue;

import um.tds.phototds.interfaz.LoginPanel;

public class PhotoTDS_App {
	private static final String RUTA = "C:\\Program Files (x86)\\ServidorPersistenciaH2\\ServidorPersistenciaH2\\ServidorPersistenciaH2.jar";
	public static void main(final String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Runtime run = Runtime.getRuntime();
				try {
					String [] cmdServPers = {"java", "-jar", RUTA };
					run.exec(cmdServPers);
					LoginPanel ventana = new LoginPanel();
					ventana.mostrarVentana();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
