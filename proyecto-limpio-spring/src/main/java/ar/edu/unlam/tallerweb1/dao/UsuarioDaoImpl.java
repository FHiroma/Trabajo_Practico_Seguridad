package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioLog;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import javax.inject.Inject;
import org.springframework.security.crypto.bcrypt.*;

// implelemtacion del DAO de usuarios, la anotacion @Repository indica a Spring que esta clase es un componente que debe
// ser manejado por el framework, debe indicarse en applicationContext que busque en el paquete ar.edu.unlam.tallerweb1.dao
// para encontrar esta clase.
@Repository("usuarioDao")
public class UsuarioDaoImpl implements UsuarioDao {

	// Como todo dao maneja acciones de persistencia, normalmente estará
	// inyectado el session factory de hibernate
	// el mismo está difinido en el archivo hibernateContext.xml
	@Inject
	private SessionFactory sessionFactory;
	@Inject
	private ServicioLog servicioLog;

	@Override
	public Usuario consultarUsuario(Usuario usuario) {

		// Se obtiene la sesion asociada a la transaccion iniciada en el
		// servicio que invoca a este metodo y se crea un criterio
		// de busqueda de Usuario donde el email y password sean iguales a los
		// del objeto recibido como parametro
		// uniqueResult da error si se encuentran más de un resultado en la
		// busqueda.
		final Session session = sessionFactory.getCurrentSession();
		Usuario u = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", usuario.getEmail()))
				.uniqueResult();
		if (u != null) {
			if (BCrypt.checkpw(usuario.getPassword(), u.getPassword())) {
				return u;
			} else {
				u.setIntentos(u.getIntentos() + 1);
				session.update(u);
				if (u.getIntentos() == 3) {
					u.setEstado(false);
					session.update(u);
				}
				servicioLog.guardarRegistro("fallo-login", u.getId());
				return null;
			}
		}
		return null;
	}
}
