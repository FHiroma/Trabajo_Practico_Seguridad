package ar.edu.unlam.tallerweb1.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import javax.inject.Inject;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import ar.edu.unlam.tallerweb1.modelo.PasswordResetToken;
import ar.edu.unlam.tallerweb1.modelo.PasswordsHistoricas;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Repository("BMUsuarioDao")
public class BMUsuarioDaoImpl implements BMUsuarioDao {

	@Inject
	private SessionFactory sessionFactory;

	@Override
	public void modificarUsuario(Long idUsuario, Usuario usuario) {
		Usuario u = (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("id", idUsuario)).uniqueResult();
		u.setNombre(usuario.getNombre());
		u.setApellido(usuario.getApellido());
		u.setPassword(usuario.getPassword());
		sessionFactory.getCurrentSession().update(u);
	}

	@Override
	public Usuario verificarUsuarioEmail(String mail) {
		Usuario u = (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("email", mail)).uniqueResult();
		return u;
	}

	@Override
	public boolean cambiarClave(String token, String password) {
		PasswordResetToken t = (PasswordResetToken) sessionFactory.getCurrentSession()
				.createCriteria(PasswordResetToken.class).add(Restrictions.eq("token", token)).uniqueResult();
		Usuario u = (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("id", t.getUsuario().getId())).uniqueResult();
		PasswordsHistoricas passwordHistorica = (PasswordsHistoricas) sessionFactory.getCurrentSession()
				.createCriteria(PasswordsHistoricas.class).add(Restrictions.eq("usuario", u))
				.add(Restrictions.eq("password", BCrypt.hashpw(password, u.getSalt()))).uniqueResult();
		if (passwordHistorica == null) {
			u.setPassword(BCrypt.hashpw(password, u.getSalt()));
			sessionFactory.getCurrentSession().update(u);
			PasswordsHistoricas passHistorica = new PasswordsHistoricas();
			passHistorica.setUsuario(u);
			passHistorica.setPassword(BCrypt.hashpw(password, u.getSalt()));
			sessionFactory.getCurrentSession().save(passHistorica);
		}
		@SuppressWarnings("unchecked")
		List<PasswordsHistoricas> listaPasswordsHistoricas = (List<PasswordsHistoricas>) sessionFactory
				.getCurrentSession().createCriteria(PasswordsHistoricas.class).add(Restrictions.eq("usuario", u))
				.list();
		if (listaPasswordsHistoricas.size() > 2) {
			sessionFactory.getCurrentSession().delete(listaPasswordsHistoricas.get(0));	
		}
		return true;
	}

	@Override
	public void crearTxt(String mensaje, Long id) {

		try {
			File statText = new File(
					"C:/Users/gonza/workspace/Trabajo_Practico_Seguridad/proyecto-limpio-spring/textos/usuario" + id
							+ "_text.txt");
			FileOutputStream is = new FileOutputStream(statText);
			OutputStreamWriter osw = new OutputStreamWriter(is);
			Writer w = new BufferedWriter(osw);
			w.write(mensaje);
			w.close();
		} catch (IOException e) {
			System.err.println("Problem writing to the file statsTest.txt");
		}
	}

	@Override
	public Usuario recuperarUsuarioId(Long id) {
		Usuario u = (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		return u;
	}

	@Override
	public Usuario recuperarUsuarioConIdYPassword(Long id, String password) {
		;
		Usuario usuario = (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		if (usuario != null) {
			if (BCrypt.checkpw(password, usuario.getPassword())) {
				return usuario;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public boolean cambiarClaveDeUsuario(Usuario usuario, String password1) {
		Usuario u = (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("id", usuario.getId())).uniqueResult();
		PasswordsHistoricas password = (PasswordsHistoricas) sessionFactory.getCurrentSession()
				.createCriteria(PasswordsHistoricas.class).add(Restrictions.eq("usuario", u))
				.add(Restrictions.eq("password", BCrypt.hashpw(password1, usuario.getSalt()))).uniqueResult();
		if (password == null) {
			u.setPassword(BCrypt.hashpw(password1, u.getSalt()));
			sessionFactory.getCurrentSession().update(u);
			PasswordsHistoricas passwordHistorica = new PasswordsHistoricas();
			passwordHistorica.setUsuario(u);
			passwordHistorica.setPassword(u.getPassword());
			sessionFactory.getCurrentSession().save(passwordHistorica);
			@SuppressWarnings("unchecked")
			List<PasswordsHistoricas> listaPasswordsHistoricas = (List<PasswordsHistoricas>) sessionFactory
					.getCurrentSession().createCriteria(PasswordsHistoricas.class).add(Restrictions.eq("usuario", u))
					.list();
			if (listaPasswordsHistoricas.size() > 2) {
				sessionFactory.getCurrentSession().delete(listaPasswordsHistoricas.get(0));
			}
			return true;
		} else {	
			return false;
		}
	}
}
