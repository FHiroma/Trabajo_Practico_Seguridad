package ar.edu.unlam.tallerweb1.dao;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Log;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Repository("automatizacionDao")
public class AutomatizacionDaoImpl implements AutomatizacionDao{
	@Inject
    private SessionFactory sessionFactory;
	
	@Override
	public void verificarUsuariosInactivos() {
		Session sesion = sessionFactory.getCurrentSession();
		//4 semanas en vez de 90 dìas para verificaciòn.
		Integer horasLimite = -672;
		Calendar fechaLimite = Calendar.getInstance();
		fechaLimite.add(Calendar.HOUR, horasLimite);
		Criteria criteria = sesion.createCriteria(Log.class, "log1");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Log.class, "log2");
		detachedCriteria.add(Property.forName("log2.id").eqProperty("log1.id"));
		detachedCriteria.setProjection(Projections.distinct(Projections.id()));
		detachedCriteria.add(Restrictions.gt("fechaHora", fechaLimite.getTime()));
		criteria.add(Subqueries.notExists(detachedCriteria));
		@SuppressWarnings("unchecked")
		List<Log> list = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		Set<Log> lista = new HashSet<Log>(list);
		for(Log log: lista){
			Usuario u = (Usuario)sesion.createCriteria(Usuario.class).add(Restrictions.idEq(log.getUsuario().getId())).uniqueResult();
			u.setEstado(false);
			sesion.update(u);
		}
	}

}
