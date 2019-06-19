package ar.edu.unlam.tallerweb1.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.inject.Inject;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
		PasswordResetToken t= (PasswordResetToken) sessionFactory.getCurrentSession()
				.createCriteria(PasswordResetToken.class)
				.add(Restrictions.eq("token", token))
				.uniqueResult();
		Usuario u= (Usuario) sessionFactory.getCurrentSession()
				.createCriteria(Usuario.class)
				.add(Restrictions.eq("id", t.getUsuario().getId()))
				.uniqueResult();
		u.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
		sessionFactory.getCurrentSession().update(u);
		return true;
	}

	@Override
	public void crearTxt(String mensaje, Long id) {
		
	  try {
		  File statText = new File("C:/Users/gonza/workspace/Trabajo_Practico_Seguridad/proyecto-limpio-spring/textos/usuario"+id+"_text.txt");
		  FileOutputStream is = new FileOutputStream(statText);
		  OutputStreamWriter osw = new OutputStreamWriter(is);
		  Writer w = new BufferedWriter(osw);
		  w.write(mensaje);
		  w.close();
		  }catch (IOException e) {
		  System.err.println("Problem writing to the file statsTest.txt");
		  }	
	}

	@Override
	public Usuario recuperarUsuarioId(Long id) {
		Usuario u= (Usuario)sessionFactory.getCurrentSession()
				.createCriteria(Usuario.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
		return u;
	}
}
