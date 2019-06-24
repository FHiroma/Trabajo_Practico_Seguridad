package ar.edu.unlam.tallerweb1.servicios;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.dao.AutomatizacionDao;

@Service("ServicioAutomatizado")
@EnableScheduling
@Transactional
public class ServicioAutomatizadoImpl implements ServicioAutomatizado{

	@Inject
	private AutomatizacionDao autoDao;
	
	@Scheduled(fixedDelay = 86400000) //1 día
	@Override
	public void verificarUsuariosInactivos() {
		autoDao.verificarUsuariosInactivos();
	}

}
