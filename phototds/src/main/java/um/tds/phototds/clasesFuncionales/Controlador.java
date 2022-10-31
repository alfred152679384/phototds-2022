package um.tds.phototds.clasesFuncionales;

import java.time.LocalDate;

import javax.swing.JTextField;

public final class Controlador {
	private static Controlador singleton;
	private RepoUsuarios repoUsers;

	private Controlador() {
		repoUsers = new RepoUsuarios();
	}

	public static Controlador getSingleton() {
		if(singleton == null) {
			singleton = new Controlador();
		}
		return singleton;
	}

	public boolean loginUser(String u, String p) {
		return repoUsers.login(u, p);
	}

	public boolean registerUser(String email, String nomb, String usuario, String cont, String feNa, String pres) {//Hay que ampliar los datos con los campos que se piden
		Usuario u = new Usuario(email,nomb,usuario,cont,null,null);
		return repoUsers.addUsuario(u);
	}

	/*
	 * private JTextField txtEmail;
	private JTextField txtNombre;
	private JTextField txtUsuario;
	private JTextField txtCont;
	private JTextField txtFeNa;
	 */

}
