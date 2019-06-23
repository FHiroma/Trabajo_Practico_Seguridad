package ar.edu.unlam.tallerweb1.servicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.unlam.tallerweb1.dao.BMUsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Service("servicioModificacionUsuario")
@Transactional

public class ServicioBMUsuarioImpl implements ServicioBMUsuario {
	
	@Inject
	private BMUsuarioDao ServicioBMUsuarioDao;
	
	@Override
	public void modificarUsuario (Long idUsuario, Usuario usuario) {
		ServicioBMUsuarioDao.modificarUsuario(idUsuario, usuario);
	}

	@Override
	public Usuario verificarUsuarioEmail(String mail) {
		return ServicioBMUsuarioDao.verificarUsuarioEmail(mail);
	}

	@Override
	public boolean cambiarClave(String token, String password) {
		if(password.length()<12){
			return false;
		}else if(password.length()>=12){
			
			  File commonPass = null;
		      FileReader fr = null;
		      BufferedReader br = null;

		      try {
		         // Apertura del fichero y creacion de BufferedReader para poder
		    	 //Indico localizaci�n del txt a comparar
		    	  commonPass = new File ("C:/Users/gabri/miau/Trabajo_Practico_Seguridad/pass10000.txt");
		         
		         fr = new FileReader (commonPass);
		         br = new BufferedReader(fr);

		         // Lectura del fichero
		         String linea;
		         String aux;
		         while((linea=br.readLine())!=null){
		            //Esta linea es para controlar por consola que se lea correctamente el archivo
		        	 //System.out.println(linea);
		        	 aux = linea;
		        	
		        	 //Valida que el pass no se encuentre en el archivo
    	        	 if(aux.equals(password)){
							return false;
							
						}
		         }
		      }
		      catch(IOException e){
		         return false;
		      }finally{
		         // Se cierra el fichero
		         try{                    
		            if( null != fr ){   
		               fr.close();     
		            }                  
		         }catch (IOException e2){ 
		            return false;
		         }
		      }
		      //Se valida fortaleza de pass
		       int  cont= 0;
		                if (password.matches(".*[a-zA-Z].*")&&password.matches(".*[0-9].*")&&password.matches(".*[!,%,&,@,#,$,^,*,?,_,~].*")) {
		                    cont=1; 
		                    } 
		                if(cont>=1){
		                	ServicioBMUsuarioDao.cambiarClave(token,password);
		                	return true;
		                } else{
		                	return false;
		                }
		} else{
			return false;
		} 
	}
	@Override
	public void crearTxt(String mensaje,Long id){
		ServicioBMUsuarioDao.crearTxt(mensaje,id);
	}

	@Override
	public Usuario recuperarUsuarioId(Long id) {
		return ServicioBMUsuarioDao.recuperarUsuarioId(id);
	}

	@Override
	public Usuario recuperarUsuarioConIdYPassword(Long id, String password) {
		return ServicioBMUsuarioDao.recuperarUsuarioConIdYPassword(id, password);
	}

	@Override
	public boolean cambiarClaveDeUsuario(Usuario usuario, String password1) {
		if(password1.length()<12){
			return false;
		}else if(password1.length()>=12){
			
			  File commonPass = null;
		      FileReader fr = null;
		      BufferedReader br = null;

		      try {
		         // Apertura del fichero y creacion de BufferedReader para poder
		    	 //Indico localizaci�n del txt a comparar
		    	  commonPass = new File ("C:/Users/gabri/miau/Trabajo_Practico_Seguridad/pass10000.txt");
		         
		         fr = new FileReader (commonPass);
		         br = new BufferedReader(fr);

		         // Lectura del fichero
		         String linea;
		         String aux;
		         while((linea=br.readLine())!=null){
		            //Esta linea es para controlar por consola que se lea correctamente el archivo
		        	 //System.out.println(linea);
		        	 aux = linea;
		        	
		        	 //Valida que el pass no se encuentre en el archivo
    	        	 if(aux.equals(password1)){
							return false;
							
						}
		         }
		      }
		      catch(IOException e){
		         return false;
		      }finally{
		         // Se cierra el fichero
		         try{                    
		            if( null != fr ){   
		               fr.close();     
		            }                  
		         }catch (IOException e2){ 
		            return false;
		         }
		      }
		      //Se valida fortaleza de pass
		       int  cont= 0;
		                if (password1.matches(".*[a-zA-Z].*")&&password1.matches(".*[0-9].*")&&password1.matches(".*[!,%,&,@,#,$,^,*,?,_,~].*")) {
		                    cont=1; 
		                    } 
		                if(cont>=1){
		                	ServicioBMUsuarioDao.cambiarClaveDeUsuario(usuario, password1);
		                	return true;
		                } else{
		                	return false;
		                }
		} else{
			return false;
		} 
	}
}
