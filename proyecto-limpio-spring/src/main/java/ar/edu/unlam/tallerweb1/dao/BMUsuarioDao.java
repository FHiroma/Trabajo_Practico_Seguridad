package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface BMUsuarioDao {
	void modificarUsuario(Long idUsuario, Usuario usuario);
	void recuperarPassword(String email, String password);
	void crearTxt(String mensaje, Long id);
}
