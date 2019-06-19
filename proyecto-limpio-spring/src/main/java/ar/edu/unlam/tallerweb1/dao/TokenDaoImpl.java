package ar.edu.unlam.tallerweb1.dao;

import java.util.Date;
import java.util.UUID;
import javax.inject.Inject;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.PasswordResetToken;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Repository("tokenDao")
public class TokenDaoImpl implements TokenDao{
	
	@Inject
    private SessionFactory sessionFactory;

	@Override
	public PasswordResetToken crearToken(Usuario u) {
		PasswordResetToken token= new PasswordResetToken();
		token.setUsuario(u);
		token.setToken(UUID.randomUUID().toString());
		token.setExpiryDate(3);
		sessionFactory.getCurrentSession().save(token);
		return token;
	}

	@Override
	public Boolean verificarToken(String token) {
			 PasswordResetToken tokenTabla= (PasswordResetToken) sessionFactory.getCurrentSession()
				.createCriteria(PasswordResetToken.class)
				.add(Restrictions.eq("token", token))
				.uniqueResult();
			 if(tokenTabla.isExpired()){
					return false;
				}else{
					return true;
				}
		}

	@Override
	public Usuario recuperarUsuarioConToken(String token) {
		 PasswordResetToken tokenTabla= (PasswordResetToken) sessionFactory.getCurrentSession()
					.createCriteria(PasswordResetToken.class)
					.add(Restrictions.eq("token", token))
					.uniqueResult();
		 Long id = tokenTabla.getUsuario().getId();
		 Usuario usuario = (Usuario) sessionFactory.getCurrentSession()
				 .createCriteria(Usuario.class)
				 .add(Restrictions.eq("id", id))
				 .uniqueResult();
		 return usuario;
	}
}

