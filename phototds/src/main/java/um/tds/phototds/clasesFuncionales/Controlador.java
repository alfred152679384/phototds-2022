package um.tds.phototds.clasesFuncionales;

import javax.swing.JTextField;

public final class Controlador {
	private static Controlador singleton;
	
	private Controlador() {

	}
	
	public static Controlador getSingleton() {
		if(singleton == null) {
			singleton = new Controlador();
		}
		return singleton;
	}
	
	public boolean loginUser(String u, String p) {
		System.out.println(u+"--"+p);
		return false;
	}
	
	public boolean registerUser(String nomb, String u, String p, String feNa) {//Hay que ampliar los datos con los campos que se piden
		System.out.println(nomb+u+p+feNa);
		return true;
	}
	
	/*
	 * private JTextField txtEmail;
	private JTextField txtNombre;
	private JTextField txtUsuario;
	private JTextField txtCont;
	private JTextField txtFeNa;
	 */
	
}
