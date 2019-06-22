package ar.edu.unlam.tallerweb1.servicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.controladores.ControladorLogin;
import ar.edu.unlam.tallerweb1.dao.RegistrarUsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Service("servicioRegistrarUsuario")
@Transactional
public class ServicioRegistrarUsuarioImpl implements ServicioRegistrarUsuario {
	
	final static Logger logger = Logger.getLogger(ServicioRegistrarUsuarioImpl.class);
	
	@Inject
	private RegistrarUsuarioDao servicioRegitrarUsuarioDao;
	
	@SuppressWarnings({ "resource" })
	@Override
	public boolean registrarUsuario(Usuario usuario) {
				
		String pass = usuario.getPassword();
		
		if(pass.length()<12){
			logger.warn("Error al cambiar la password: Contraseña muy corta"); 
			return false;
		}else if(pass.length()>=12){
			
			  File commonPass = null;
		      FileReader fr = null;
		      BufferedReader br = null;

		      try {
		         // Apertura del fichero y creacion de BufferedReader para poder
		    	 //Indico localización del txt a comparar
		         commonPass = new File ("C:/Users/gonza/workspace/Trabajo_Practico_Seguridad/proyecto-limpio-spring/pass10000.txt");
		         fr = new FileReader (commonPass);
		         br = new BufferedReader(fr);

		         // Lectura del fichero
		         String linea;
		         String aux;
		         while((linea=br.readLine())!=null){
		            //Esta linea es para controlar por consola que se lea correctamente el archivo
		        	// System.out.println(linea);
		        	 aux = linea;
		        	
		        	 //Valida que el pass no se encuentre en el archivo
    	        	 if(aux.equals(pass)){
    	        		 logger.warn("Error al cambiar la password: Contraseña muy predecible");
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
		                if (pass.matches(".*[a-zA-Z].*")&&pass.matches(".*[0-9].*")&&pass.matches(".*[!,%,&,@,#,$,^,*,?,_,~].*")) {
		                    cont=1; 
		                    } 
		                if(cont>=1){
		                	servicioRegitrarUsuarioDao.registrarUsuario(usuario);
		                	return true;
		                } else{
		                	logger.warn("Error al cambiar la password: Contraseña débil");
		                	return false;
		                }
		
		} else{
			return false;
		} 
		
	}
}
