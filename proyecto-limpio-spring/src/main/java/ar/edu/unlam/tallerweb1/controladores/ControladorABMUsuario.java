package ar.edu.unlam.tallerweb1.controladores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioAdmin;
import ar.edu.unlam.tallerweb1.servicios.ServicioBMUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioLog;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;
import ar.edu.unlam.tallerweb1.servicios.ServicioRecaptcha;
import ar.edu.unlam.tallerweb1.servicios.ServicioRegistrarUsuario;

@Controller
public class ControladorABMUsuario {
	
	@Inject
	private ServicioBMUsuario servicioModificacionUsuario;
	@Inject 
	private ServicioBMUsuario servicioRecuperarPassword;
	@Inject
	private ServicioLog servicioLog;
	@Inject
	private ServicioRegistrarUsuario servicioRegistrarUsuario;
	@Inject
	private ServicioLogin servicioLogin;
	@Inject
	private ServicioRecaptcha servicioRecaptcha;
	@Inject
	private ServicioBMUsuario servicioCrearTxt;

	
	@RequestMapping("/actualizar-datos-usuario")
	public ModelAndView actualizarDatosUsuario(){
		ModelMap modelo = new ModelMap();	
		Usuario usuario = new Usuario();
		modelo.put("usuario", usuario);
		return new ModelAndView("formularioActualizarDatos", modelo);
	}
	
	@RequestMapping(path ="/modificar-datos-usuario", method = RequestMethod.POST)
	public ModelAndView modificarDatosUsuario(@ModelAttribute("usuario") Usuario usuario , HttpServletRequest request){
		Long id=(Long)request.getSession().getAttribute("id");
		servicioModificacionUsuario.modificarUsuario(id, usuario);
		servicioLog.guardarRegistro("modificar-datos", id);
		return new ModelAndView("mensajeActualizacion");
	}
	
	@RequestMapping("/recuperar-password")
	public ModelAndView recuperarContraseña(){
		ModelMap modelo = new ModelMap();	
		Usuario usuario = new Usuario();
		modelo.put("usuario", usuario);
		return new ModelAndView("recuperarPassword",modelo);
	}
	
	@RequestMapping(path="/validar-email", method = RequestMethod.POST)
	public ModelAndView validarEmail(@ModelAttribute("usuario") Usuario usuario){
		String email= usuario.getEmail();
		String password=usuario.getPassword();
		servicioRecuperarPassword.recuperarPassword(email, password);
		ModelMap model = new ModelMap();	
		model.put("password", "Actualizacion de contrase�a exitosa, Ingrese su usuario y su nueva clave");
				
		return new ModelAndView("login", model);
	}
	
	@RequestMapping("/registro")
	public ModelAndView registrarUsuario(){	
		ModelMap modelo = new ModelMap();
		Usuario usuario = new Usuario();
		modelo.put("usuario", usuario);
		return new ModelAndView("registrarUsuario", modelo);
	}
	
	@RequestMapping(path="registrar-usuario", method= RequestMethod.POST)
	public ModelAndView insertarUsuario(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request, HttpServletResponse response) throws Exception{
		boolean validarPass = servicioRegistrarUsuario.registrarUsuario(usuario);
		String mensaje="";
		
		/* necesario para el captcha*/
			boolean isHuman = servicioRecaptcha.checkRecaptcha(request);
		/* Fin captcha */
		
		ModelMap model = new ModelMap();
		
		if(validarPass&&isHuman){
			return new ModelAndView("homeUser");
		}else{
			mensaje="Revise sus datos, no cumplen con nuestras politicas de seguridad";
			model.put("mensaje", mensaje);
			return new ModelAndView("registrarUsuario", model);
		}
			
	}
	
	@RequestMapping("/crear-texto")
    public ModelAndView crearTxt(HttpServletRequest request){
        ModelMap model = new ModelMap();
             
        Usuario usuario = new Usuario();
        
        model.put("usuario", usuario);

        return new ModelAndView("vista-txt",model);
    }

	  @RequestMapping(path="/texto-ok", method= RequestMethod.POST)
	  public ModelAndView textoOk(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request){	
	  ModelMap model = new ModelMap();   
	  String mensaje= usuario.getText();
	  Long id = (Long) request.getSession().getAttribute("id");
	  servicioCrearTxt.crearTxt(mensaje,id);
	  model.put("id",id);
	  model.put("mensaje",mensaje);
	  return new ModelAndView("texto",model);
	  }
	  

	
}