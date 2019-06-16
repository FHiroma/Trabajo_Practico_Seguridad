package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface BMUsuarioDao {
	void modificarUsuario(Long idUsuario, Usuario usuario);
	Usuario verificarUsuarioEmail(String mail);
	Boolean cambiarClave(String token,String password);
	void crearTxt(String mensaje, Long id);
}
