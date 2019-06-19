package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.PasswordResetToken;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface ServicioToken {
	PasswordResetToken crearToken(Usuario u);
	Boolean verificarToken(String token);
	Usuario recuperarUsuarioConToken(String token);
}
