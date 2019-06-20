package ar.edu.unlam.tallerweb1.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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



	@Override
	public StringBuilder leerTxt(StringBuilder sb, Long id) {
			//StringBuilder sb = new StringBuilder();
			try (BufferedReader br = Files.newBufferedReader
			(Paths.get("C:/Users/gonza/workspace/Trabajo_Practico_Seguridad/proyecto-limpio-spring/textos/usuario"+id+"_text.txt"))) {
			String line;
		
		    while ((line = br.readLine()) != null) {
		    sb.append(line).append("\n");}
		    
		    }catch(IOException e){
		    	
		    System.err.format("IOException: %s%n", e);}
			//System.out.println(sb);
			return sb;
	}
	
		
	
}
