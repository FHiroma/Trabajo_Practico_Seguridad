package ar.edu.unlam.tallerweb1.dao;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
@Repository("adminDao")
public class AdminDaoImpl implements AdminDao{
	@Inject
    private SessionFactory sessionFactory;

	@Override
	public List<Usuario> traerListadoDeUsuarios() {
		@SuppressWarnings("unchecked")
		List <Usuario> listaDeUsuarios = sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("rol", "user"))
				.list();
		return listaDeUsuarios;
	}

	@Override
	public String activarUsuario(Long id) {
		Usuario usuario = (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		usuario.setEstado(true);
		sessionFactory.getCurrentSession().update(usuario);
		return usuario.getEmail();
	}
	@Override
	public String desactivarUsuario(Long id) {
		Usuario usuario = (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		usuario.setEstado(false);
		sessionFactory.getCurrentSession().update(usuario);
		return usuario.getEmail();
	}

	
}
