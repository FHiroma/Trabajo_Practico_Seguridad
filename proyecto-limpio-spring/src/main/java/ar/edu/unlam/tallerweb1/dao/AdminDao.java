package ar.edu.unlam.tallerweb1.dao;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface AdminDao {

	List<Usuario> traerListadoDeUsuarios();
	String activarUsuario(Long id);
	String desactivarUsuario(Long id);
}
