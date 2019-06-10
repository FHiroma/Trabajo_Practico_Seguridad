package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface ServicioAdmin {
	String activarUsuario(Long id);
	String desactivarUsuario(Long id);
	void leerTxt(StringBuilder sb,Long id);
	List<Usuario> traerListadoDeUsuarios();
}
