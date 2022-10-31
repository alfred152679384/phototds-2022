package um.tds.phototds.clasesFuncionales;

import java.time.LocalDate;
import java.util.HashSet;

public class RepoUsuarios {
	private HashSet<Usuario> usuarios;
	//faltan cosas
	
	public RepoUsuarios(){
		this.usuarios = new HashSet<Usuario>();
		usuarios.add(new Usuario("admin", "admin", "admin", "admin", LocalDate.now(), null));
		usuarios.add(new Usuario("manolo@um.es","Manolo","manolo","manolo",LocalDate.now(),null));
	}
	
	public boolean login(String u, String p) {
		for(Usuario user : usuarios) {
			System.out.println(user.getUsuario()+"-"+user.getCont());
			if(user.getUsuario().equals(u)) {
				if(user.getCont().equals(p)) {
					return true;
				}
				return false;
			}
		}
		return false;
	}
	
	public boolean addUsuario(Usuario u) {
		return usuarios.add(u);
	}
	
	
}
