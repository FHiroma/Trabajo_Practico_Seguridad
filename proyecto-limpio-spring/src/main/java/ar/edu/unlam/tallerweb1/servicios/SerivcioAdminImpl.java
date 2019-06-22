package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.dao.AdminDao;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Service("servicioAdmin")
@Transactional
public class SerivcioAdminImpl implements ServicioAdmin{
	@Inject
	private AdminDao servicioAdmin;

	@Override
	public List<Usuario> traerListadoDeUsuarios() {
		return servicioAdmin.traerListadoDeUsuarios();
	}

	@Override
	public String activarUsuario(Long id) {
		return servicioAdmin.activarUsuario(id);
	}
	@Override
	public String desactivarUsuario(Long id) {
		return servicioAdmin.desactivarUsuario(id);
	}

	@Override
	public StringBuilder leerTxt(StringBuilder sb, Long id) {
		return servicioAdmin.leerTxt(sb, id);
	}
}
