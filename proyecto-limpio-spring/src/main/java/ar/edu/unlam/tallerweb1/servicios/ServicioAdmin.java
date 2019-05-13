package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface ServicioAdmin {
	List <Usuario> traerListadoDeUsuarios();
	String activarUsuario(Long id);
	String desactivarUsuario(Long id);
}
