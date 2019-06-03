package ar.edu.unlam.tallerweb1.servicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
	
	@SuppressWarnings({ "resource" })
	@Override
	public boolean registrarUsuario(Usuario usuario) {
				
		String pass = usuario.getPassword();
		
		if(pass.length()<12){
			return false;
		}else if(pass.length()>=12){
			
			  File commonPass = null;
		      FileReader fr = null;
		      BufferedReader br = null;

		      try {
		         // Apertura del fichero y creacion de BufferedReader para poder
		    	 //Indico localización del txt a comparar
		         commonPass = new File ("C://Users/gabri/miau/Trabajo_Practico_Seguridad/pass10000.txt");
//		         c://pass10000.txt
		         
		         fr = new FileReader (commonPass);
		         br = new BufferedReader(fr);

		         // Lectura del fichero
		         String linea;
		         String aux;
		         while((linea=br.readLine())!=null){
		            //Esta linea es para controlar por consola que se lea correctamente el archivo
		        	 System.out.println(linea);
		        	 aux = linea;
		        	
		        	 //Valida que el pass no se encuentre en el archivo
    	        	 if(aux.equals(pass)){
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
		    servicioRegitrarUsuarioDao.registrarUsuario(usuario);  
			return true;
		} else{
			servicioRegitrarUsuarioDao.registrarUsuario(usuario);
			return true;
		} 
		
	}
}
