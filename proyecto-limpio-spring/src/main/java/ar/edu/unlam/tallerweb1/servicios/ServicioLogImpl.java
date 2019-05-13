package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.dao.LogDao;
import ar.edu.unlam.tallerweb1.modelo.Log;

@Service("servicioLog")
@Transactional
public class ServicioLogImpl implements ServicioLog{
	@Inject
	private LogDao servicioLog;
	
	@Override
	public void guardarRegistro(String accion,Long id) {
		servicioLog.guardarRegistro(accion,id);
	}

	@Override
	public List<Log> traerRegistrosDelUsuario(Long id) {
		return servicioLog.verHistorialDeActividad(id);
	}

}
