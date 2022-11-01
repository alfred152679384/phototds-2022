package um.tds.phototds.controlador;

import java.time.LocalDate;

import javax.swing.JTextField;

import um.tds.phototds.clasesFuncionales.RepoUsuarios;
import um.tds.phototds.clasesFuncionales.Usuario;

public enum Controlador {
	INSTANCE;
//	private static Controlador singleton;
	private RepoUsuarios repoUsers;

	private Controlador() {
		repoUsers = new RepoUsuarios();
	}

//	public static Controlador getSingleton() {
//		if(singleton == null) {
//			singleton = new Controlador();
//		}
//		return singleton;
//	}

	public boolean loginUser(String u, String p) {
		return repoUsers.login(u, p);
	}

	public boolean registerUser(String email, String nomb, String usuario, String cont, String feNa, String pres) {//Hay que ampliar los datos con los campos que se piden
		Usuario u = new Usuario(email,nomb,usuario,cont,null,null);
		return repoUsers.addUsuario(u);
	}

}
