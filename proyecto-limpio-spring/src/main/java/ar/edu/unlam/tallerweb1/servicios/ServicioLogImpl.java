package ar.edu.unlam.tallerweb1.servicios;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.dao.LogDao;

@Service("servicioLog")
@Transactional
public class ServicioLogImpl implements ServicioLog{
	@Inject
	private LogDao servicioLog;
	
	@Override
	public void guardarRegistro(String accion,Long id) {
		servicioLog.guardarRegistro(accion,id);
	}

}
