package ar.edu.unlam.tallerweb1.dao;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Log;

public interface LogDao {
	void guardarRegistro(String accion, Long id);
	List<Log> verHistorialDeActividad(Long id);
}
