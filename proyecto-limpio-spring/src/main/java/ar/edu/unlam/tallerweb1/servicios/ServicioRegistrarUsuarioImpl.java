package ar.edu.unlam.tallerweb1.servicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.unlam.tallerweb1.dao.RegistrarUsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Service("servicioRegistrarUsuario")
@Transactional
public class ServicioRegistrarUsuarioImpl implements ServicioRegistrarUsuario {
	
	@Inject
	private RegistrarUsuarioDao servicioRegitrarUsuarioDao;
	
	@Override
	public boolean registrarUsuario(Usuario usuario) {
				
		//Indico localización del txt a comparar
		File commonPass = new File("c:/pass10000.txt");
		String pass = usuario.getPassword();
		if(pass.length()<12){
			return false;
		} else if(pass.length()>=12){
			try{
				FileReader fr = new FileReader(commonPass); 
				BufferedReader br = new BufferedReader(fr);
				
				String linea = br.readLine();
				
				while(linea != null) {
				    linea = br.readLine();
				  }
				br.close();
				 
				}catch(Exception e) {
					  return false;
				}
				return true;
			}else{
				servicioRegitrarUsuarioDao.registrarUsuario(usuario);
				return true;
			}
	}
}
