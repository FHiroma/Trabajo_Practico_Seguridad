package ar.edu.unlam.tallerweb1.dao;

import javax.inject.Inject;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ar.edu.unlam.tallerweb1.modelo.PasswordResetToken;
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

	@Override
	public Usuario verificarUsuarioEmail(String mail) {
		Usuario u = (Usuario) sessionFactory.getCurrentSession()
				.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", mail))
				.uniqueResult();
		return u;
	}

	@Override
	public Boolean cambiarClave(String token, String password) {
		PasswordResetToken t=(PasswordResetToken) sessionFactory.getCurrentSession()
				.createCriteria(PasswordResetToken.class)
				.add(Restrictions.eq("token", token))
				.uniqueResult();
		if(t!=null){
			Long id=t.getUsuario().getId();
			Usuario u= (Usuario) sessionFactory.getCurrentSession()
					.createCriteria(Usuario.class)
					.add(Restrictions.eq("id", id))
					.uniqueResult();
			if(u!=null){
				u.setPassword(password);
				sessionFactory.getCurrentSession().update(u);
				return true;
			}
		}
		return false;
	}

}
