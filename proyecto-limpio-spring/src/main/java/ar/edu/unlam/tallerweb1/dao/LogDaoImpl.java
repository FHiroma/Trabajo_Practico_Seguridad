package ar.edu.unlam.tallerweb1.dao;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Log;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Repository("LogDao")
public class LogDaoImpl implements LogDao{

	@Inject
    private SessionFactory sessionFactory;
	
	@Override
	public void guardarRegistro(String accion, Long id) {
		Usuario usuario = (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		Log log = new Log();
		log.setUsuario(usuario);
		Date fechaYHora = new Date();
		log.setFechaHora(fechaYHora);
		Integer codigo;
		switch(accion){
		case "login":
			codigo = 1;
			break;
		case "logout":
			codigo = 2;
			break;
		case "modificar-datos":
			codigo = 3;
			break;
		case "modificar-texto":
			codigo = 4;
			break;
		case "recuperar-contraseña":
			codigo = 5;
			break;
		case "registrado":
			codigo = 6;
			break;
		default:
			codigo = 0;
		}
		log.setCodigo(codigo);
		sessionFactory.getCurrentSession().save(log);
	}

	@Override
	public List<Log> verHistorialDeActividad(Long id) {
		@SuppressWarnings("unchecked")
		List<Log> lista = sessionFactory.getCurrentSession().createCriteria(Log.class)
				.createAlias("usuario", "usuarioDelLog")
				.add(Restrictions.eq("usuarioDelLog.id", id))
				.list();
		return lista;
	}
	
}
