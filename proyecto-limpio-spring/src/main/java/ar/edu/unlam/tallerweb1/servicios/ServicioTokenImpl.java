package ar.edu.unlam.tallerweb1.servicios;

import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.unlam.tallerweb1.dao.TokenDao;
import ar.edu.unlam.tallerweb1.modelo.PasswordResetToken;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Service("servicioToken")
@Transactional
public class ServicioTokenImpl implements ServicioToken {

	@Inject
	private TokenDao servicioTokenDao;
	
	@Override
	public PasswordResetToken crearToken(Usuario u) {
		return servicioTokenDao.crearToken(u);
	}

	@Override
	public Boolean verificarToken(String token) {
		return servicioTokenDao.verificarToken(token);
	}

	@Override
	public PasswordResetToken recuperarUsuarioConToken(String token) {
		return servicioTokenDao.recuperarUsuarioConToken(token);
	}

}
