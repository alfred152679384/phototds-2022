package um.tds.phototds.clasesFuncionales;

import java.util.HashSet;

public class RepoUsuarios {
	private HashSet<Usuario> usuarios;
	//faltan cosas
	
	public RepoUsuarios(){
		this.usuarios = new HashSet<Usuario>();
	}
	
	public boolean addUsuario(Usuario u) {
		return usuarios.add(u);
	}
}
