package ar.edu.unlam.tallerweb1.servicios;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface ServicioBMUsuario {
	void modificarUsuario(Long idUsuario, Usuario usuario);
	void recuperarPassword(String email, String password);
}
