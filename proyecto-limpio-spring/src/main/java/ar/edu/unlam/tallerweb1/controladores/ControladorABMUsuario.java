package ar.edu.unlam.tallerweb1.controladores;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.PasswordResetToken;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.EmailService;
import ar.edu.unlam.tallerweb1.servicios.ServicioBMUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioLog;
import ar.edu.unlam.tallerweb1.servicios.ServicioRecaptcha;
import ar.edu.unlam.tallerweb1.servicios.ServicioRegistrarUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioToken;
import org.apache.log4j.*;

@Controller
public class ControladorABMUsuario {
	
	final static Logger logger = Logger.getLogger(ControladorABMUsuario.class); 
	
	@Inject
	private ServicioBMUsuario servicioModificacionUsuario;
	@Inject
	private ServicioLog servicioLog;
	@Inject
	private ServicioRegistrarUsuario servicioRegistrarUsuario;
	@Inject 
	private ServicioBMUsuario servicioVerificarEmailUsuario;
	@Inject
	private ServicioToken servicioCrearToken;
	@Inject
	private ServicioToken servicioVerificarToken;
	@Inject
	private ServicioBMUsuario servicioCambiarClave;
	@Inject 
	private ServicioRecaptcha servicioRecaptcha;
	@Inject 
	private ServicioBMUsuario servicioCrearTxt;
	@Inject
	private ServicioBMUsuario servicioCambiarClaveDeUsuario;
	@Inject
	private EmailService servicioEnviarMail;
	@Inject
	private ServicioBMUsuario servicioRecuperarUsuarioConIdYPassword;
	
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
	public ModelAndView validarEmail(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request){
		String mail= usuario.getEmail();
		if(mail.isEmpty()){
			logger.info("Campo Mail vacio");
			ModelMap modelo= new ModelMap();
			String mensaje="Ingrese su Email para recuperar su contraseña";
			modelo.put("error", mensaje);
			return new ModelAndView("recuperarPassword", modelo);
		}
		Usuario u=servicioVerificarEmailUsuario.verificarUsuarioEmail(mail);
		/* necesario para el captcha*/
		boolean isHuman = servicioRecaptcha.checkRecaptcha(request);
	/* Fin captcha */
		if(u != null && isHuman){
			logger.info("ValidarEmail:" + usuario.toString());
			PasswordResetToken token=servicioCrearToken.crearToken(u);
			servicioEnviarMail.send(u.getEmail(), "Recuperar Password"
					,"http://localhost:8080/proyecto-limpio-spring/solicitar-cambio-clave?token="+token.getToken());
//			a("Enlace").withHref("http://localhost:8080/proyecto-limpio-spring/solicitar-cambio-clave?token="+token.getToken())
			ModelMap modelo = new ModelMap();
			modelo.put("usuario", u);
			modelo.put("exito", "Solicitud de cambio de contraseña Usuario:");
			return new ModelAndView("exito", modelo);
		} else {
			logger.warn("NO ValidarEmail: No existe usuario con Email" + usuario.getEmail());
			ModelMap modelo= new ModelMap();
			String mensaje="No existe usuario con ese Email";
			modelo.put("error", mensaje);
			return new ModelAndView("recuperarPassword", modelo);
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
			logger.info("Se ha registrado el Usuario:" +usuario.toString());
			return new ModelAndView("redirect:/login");
		}else{
			logger.warn("No valida password" + usuario.toString());
			mensaje="Revise sus datos, no cumplen con nuestras politicas de seguridad";
			model.put("mensaje", mensaje);
			return new ModelAndView("registrarUsuario", model);
		}		
	}
	
	@RequestMapping("/solicitar-cambio-clave")
	public ModelAndView solicitarCambioClave(@RequestParam ("token") String token){
		boolean verificarToken=servicioVerificarToken.verificarToken(token);
		if(verificarToken==true){
			ModelMap modelo= new ModelMap();
			modelo.put("token", token);
			logger.info("Token OK:" + token.toString());
			return new ModelAndView("formularioCambiarClaveUsuario",modelo);
		}else{
			Usuario usuario =servicioVerificarToken.recuperarUsuarioConToken(token);
			ModelMap modelo= new ModelMap();
			modelo.put("usuario", usuario);
			modelo.put("error", "Token expirado");
			logger.info("Token expirado:" + token.toString());
			return new ModelAndView("vistaTokenExpirado",modelo);
		}
	}
	
	@RequestMapping(path="/cambiar-clave", method = RequestMethod.POST)
	public ModelAndView cambiarClave(@ModelAttribute ("token") String token,
									 @ModelAttribute ("password") String password){
		servicioCambiarClave.cambiarClave(token, password);
		Usuario usuario =servicioVerificarToken.recuperarUsuarioConToken(token);
		servicioEnviarMail.send(usuario.getEmail(), "Cambio de clave", "Se ha cambiado la clave de su cuenta" + usuario.getEmail());
		ModelMap modelo= new ModelMap();
		modelo.put("error", "Cambio de clave correcto!."
				+ "Vuelva a introducir sus datos para iniciar sesion");
		return new ModelAndView("redirect:/login",modelo);
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
	
	@RequestMapping("/actualizar-password")
	public ModelAndView actuzalizarPassword(){	
		return new ModelAndView("vista-formulario-cambiar-clave-logeado");
	}
	
	@RequestMapping(path="/cambiar-clave-logeado", method= RequestMethod.POST)
	public ModelAndView cambiarClaveUsuarioLogeado(@ModelAttribute ("password") String password
												   , @ModelAttribute ("nvopass") String password1
												   , @ModelAttribute ("repeticion") String password2
												   , HttpServletRequest request){
		if(password.equals(null)){
			logger.warn("Campo Password:" + "cambiarClaveUsuarioLogeado");
		}
		if(password1.equals(null)){
			logger.warn("Campo nvopass:" + "cambiarClaveUsuarioLogeado");
		}
		if(password2.equals(null)){
			logger.warn("Campo repeticion:" + "cambiarClaveUsuarioLogeado");
		}
		/* necesario para el captcha*/
		boolean isHuman = servicioRecaptcha.checkRecaptcha(request);
		/* Fin captcha */
		Long id = (Long) request.getSession().getAttribute("id");
		Usuario usuario = servicioRecuperarUsuarioConIdYPassword.recuperarUsuarioConIdYPassword(id, password);
		logger.info("Usuario validado:" + usuario.toString());
		boolean validarpass= servicioCambiarClaveDeUsuario.cambiarClaveDeUsuario(usuario, password1);
		if(password1.equals(password2)&&isHuman&&validarpass){
			return new ModelAndView("redirect:/");	
		}else{
			logger.warn("No valida password" + usuario.toString());
			ModelMap model= new ModelMap();
			String mensaje="Revise sus datos, no cumplen con nuestras politicas de seguridad";
			model.put("mensaje", mensaje);
			return new ModelAndView("vista-formulario-cambiar-clave-logeado", model);
		}	
	}
}