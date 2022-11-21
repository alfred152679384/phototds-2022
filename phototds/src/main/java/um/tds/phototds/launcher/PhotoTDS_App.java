package um.tds.phototds.launcher;

import java.awt.EventQueue;

import um.tds.phototds.interfaz.LoginGUI;

public class PhotoTDS_App {
	private static final String RUTA = "E:\\UNIVERSIDAD\\3.º\\C1\\TDS\\workspace\\servidorPersistencia.bat";
	public static void main(final String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI ventana = new LoginGUI();
					ventana.mostrarVentana();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
