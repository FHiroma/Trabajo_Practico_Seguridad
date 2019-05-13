package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Log;

public interface ServicioLog {
	void guardarRegistro(String accion,Long id);
	List<Log> traerRegistrosDelUsuario(Long id);
}
