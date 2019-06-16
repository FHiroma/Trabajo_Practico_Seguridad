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

import ar.edu.unlam.tallerweb1.modelo.PasswordResetToken;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioAdmin;
import ar.edu.unlam.tallerweb1.servicios.ServicioBMUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioLog;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;
import ar.edu.unlam.tallerweb1.servicios.ServicioRecaptcha;
import ar.edu.unlam.tallerweb1.servicios.ServicioRegistrarUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioToken;


@Controller
public class ControladorABMUsuario {
	
	@Inject
	private ServicioBMUsuario servicioModificacionUsuario;
	@Inject
	private ServicioLog servicioLog;
	@Inject
	private ServicioRegistrarUsuario servicioRegistrarUsuario;
	@Inject 
	private ServicioBMUsuario servicioVerificarUsuario;
	@Inject
	private ServicioToken servicioCrearToken;
	@Inject
	private ServicioToken servicioVerificarToken;
	@Inject
	private ServicioBMUsuario servicioCambiarClave;
	
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
		
		String mail= usuario.getEmail();
		Usuario u=servicioVerificarUsuario.verificarUsuarioEmail(mail);
		if(u!=null){
			PasswordResetToken token=servicioCrearToken.crearToken(u);
			ModelMap modelo = new ModelMap();
			modelo.put("mensaje", "Se Creo Correctamente Token");
			modelo.put("token", token);
			return new ModelAndView("mailRecuperarPassword",modelo);
		}else{
			ModelMap model = new ModelMap();
			model.put("mensaje", "No hay usuario registrado con ese mail");
			return new ModelAndView("exito", model);
		}
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
	
	@RequestMapping("/solicitar-cambio-clave")
	public ModelAndView solicitarCambioClave(@RequestParam ("id") Long id,
											 @RequestParam ("token") String token){
		servicioVerificarToken.verificarToken(id,token);
		ModelMap modelo= new ModelMap();
		modelo.put("token", token);
		return new ModelAndView("formularioCambiarClaveUsuario",modelo);
	}
	
	@RequestMapping(path="/cambiar-clave", method = RequestMethod.POST)
	public ModelAndView cambiarClave(@ModelAttribute ("token") String token,
									 @ModelAttribute ("password") String password){
		servicioCambiarClave.cambiarClave(token, password);
		return new ModelAndView("exito");
	}
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