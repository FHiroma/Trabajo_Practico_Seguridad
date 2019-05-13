package ar.edu.unlam.tallerweb1.dao;

import javax.inject.Inject;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Repository("BMUsuarioDao")
public class BMUsuarioDaoImpl implements BMUsuarioDao {
	
	@Inject
    private SessionFactory sessionFactory;
	
	@Override
	public void modificarUsuario(Long idUsuario, Usuario usuario){
		Usuario u = (Usuario) sessionFactory.getCurrentSession()
		.createCriteria(Usuario.class)
		.add(Restrictions.eq("id", idUsuario))
		.uniqueResult();
		u.setNombre(usuario.getNombre());
		u.setApellido(usuario.getApellido());
		u.setPassword(usuario.getPassword());
		sessionFactory.getCurrentSession().update(u);	
	}
}
