package ar.edu.unlam.tallerweb1.dao;

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
		token.setExpiryDate(30);
		sessionFactory.getCurrentSession().save(token);
		return token;
	}

	@Override
	public void verificarToken(Long id, String token) {
		Usuario u=(Usuario)sessionFactory.getCurrentSession().createCriteria(Usuario.class)
		.add(Restrictions.eq("id", id))
		.uniqueResult();
		if(u!=null){
			sessionFactory.getCurrentSession().createCriteria(PasswordResetToken.class)
				.add(Restrictions.eq("usuario", u))
				.add(Restrictions.eq("token", token))
				.uniqueResult();
		}
	}
}
