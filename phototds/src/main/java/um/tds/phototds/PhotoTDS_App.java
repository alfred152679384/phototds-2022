package um.tds.phototds;

import java.awt.EventQueue;

import um.tds.phototds.interfaz.LoginPanel;

public class PhotoTDS_App {
	//private Controlador ctrl;
	//Crear UNA sola instancia del controlador
	public static void main(final String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPanel ventana = new LoginPanel();
					ventana.mostrarVentana();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
