package ar.edu.unlam.tallerweb1.servicios;

import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.unlam.tallerweb1.dao.BMUsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Service("servicioModificacionUsuario")
@Transactional

public class ServicioBMUsuarioImpl implements ServicioBMUsuario {
	
	@Inject
	private BMUsuarioDao ServicioBMUsuarioDao;
	
	@Override
	public void modificarUsuario (Long idUsuario, Usuario usuario) {
		ServicioBMUsuarioDao.modificarUsuario(idUsuario, usuario);
	}
}
