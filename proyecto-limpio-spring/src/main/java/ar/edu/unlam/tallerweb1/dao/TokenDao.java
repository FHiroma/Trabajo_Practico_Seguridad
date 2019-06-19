package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.PasswordResetToken;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface TokenDao {
	PasswordResetToken crearToken(Usuario u);
	Boolean verificarToken(String token);
	PasswordResetToken recuperarUsuarioConToken(String token);
}
