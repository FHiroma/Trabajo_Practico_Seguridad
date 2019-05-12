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

	
}
