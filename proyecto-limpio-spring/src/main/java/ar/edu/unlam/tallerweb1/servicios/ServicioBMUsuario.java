package ar.edu.unlam.tallerweb1.servicios;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface ServicioBMUsuario {
	void modificarUsuario(Long idUsuario, Usuario usuario);
	Usuario verificarUsuarioEmail(String mail);
	boolean cambiarClave(String token,String password);
	void crearTxt(String mensaje,Long id);
	Usuario recuperarUsuarioId(Long id);
	Usuario recuperarUsuarioConIdYPassword(Long id, String password);
	boolean cambiarClaveDeUsuario(Usuario usuario, String password1);
}
