package ar.edu.unlam.tallerweb1.servicios;

import java.util.ArrayList;

import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.unlam.tallerweb1.dao.RegistrarUsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Service("servicioRegistrarUsuario")
@Transactional
public class ServicioRegistrarUsuarioImpl implements ServicioRegistrarUsuario {
	
	@Inject
	private RegistrarUsuarioDao servicioRegitrarUsuarioDao;

	@Override
	public boolean registrarUsuario(Usuario usuario) {
		return servicioRegitrarUsuarioDao.registrarUsuario(usuario);
	}
}
