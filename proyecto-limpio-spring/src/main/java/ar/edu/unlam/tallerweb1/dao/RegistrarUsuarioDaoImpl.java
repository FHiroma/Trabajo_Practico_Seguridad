package ar.edu.unlam.tallerweb1.dao;
import ar.edu.unlam.tallerweb1.modelo.PasswordsHistoricas;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.List;
import javax.inject.Inject;

import org.springframework.security.crypto.bcrypt.*;

@Repository("registrarUsuarioDao")
public class RegistrarUsuarioDaoImpl implements RegistrarUsuarioDao{
	
	@Inject
    private SessionFactory sessionFactory;
	
	@Override
	public boolean registrarUsuario(Usuario usuario) {
		@SuppressWarnings("unchecked")
		List<Usuario> listaUsuario=(List<Usuario>)sessionFactory
				.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.like("email", usuario.getEmail()))
				.list();
		if(listaUsuario.isEmpty()){
			usuario.setRol("user");
			usuario.setEstado(true);
			usuario.setSalt();
			usuario.setPassword(BCrypt.hashpw(usuario.getPassword(), usuario.getSalt()));
			usuario.setIntentos(0);
			sessionFactory.getCurrentSession().save(usuario);
			PasswordsHistoricas password= new PasswordsHistoricas();
			password.setUsuario(usuario);
			password.setPassword(BCrypt.hashpw(usuario.getPassword(), usuario.getSalt()));
			sessionFactory.getCurrentSession().save(password);
			return true;
		}else{
			return false;
		}
	}
}
