package ar.edu.unlam.tallerweb1.dao;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository("registrarUsuarioDao")
public class RegistrarUsuarioDaoImpl implements RegistrarUsuarioDao{
	
	@Inject
    private SessionFactory sessionFactory;
	
	@Override
	public void registrarUsuario(Usuario usuario) {

		Session session = sessionFactory.getCurrentSession();
		
		usuario.setRol("user");
		usuario.setEstado(true);
		session.save(usuario);
	}
	
}
