package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.PasswordResetToken;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface ServicioToken {
	PasswordResetToken crearToken(Usuario u);
	void verificarToken(Long id,String token);
}
