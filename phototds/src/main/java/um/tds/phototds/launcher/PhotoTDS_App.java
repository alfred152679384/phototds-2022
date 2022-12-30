package um.tds.phototds.launcher;

import java.awt.EventQueue;

import um.tds.phototds.interfaz.LoginGUI;

public class PhotoTDS_App {
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
