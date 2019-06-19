package ar.edu.unlam.tallerweb1.controladores;

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
import ar.edu.unlam.tallerweb1.servicios.EmailService;
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
	private ServicioBMUsuario servicioRecuperarUsuarioId;
	@Inject
	private ServicioBMUsuario servicioCambiarClaveDeUsuario;
	@Inject
	private EmailService servicioEnviarMail;
	
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
		Usuario u=servicioVerificarEmailUsuario.verificarUsuarioEmail(mail);
		if(u != null){
			PasswordResetToken token=servicioCrearToken.crearToken(u);
			servicioEnviarMail.send(u.getEmail(), "Recuperar Password"
					,"http://localhost:8080/proyecto-limpio-spring/solicitar-cambio-clave?token="+token.getToken());
			ModelMap modelo = new ModelMap();
			modelo.put("usuario", u);
			modelo.put("exito", "Solicitud de cambio de contraseña Usuario:");
			return new ModelAndView("exito", modelo);
		} else {
			ModelMap modelo= new ModelMap();
			modelo.put("error", "No existe usuario con ese Email");
			return new ModelAndView("exito", modelo);
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
	public ModelAndView solicitarCambioClave(@RequestParam ("token") String token){
		Boolean verificarToken=servicioVerificarToken.verificarToken(token);
		if(verificarToken==true){
			ModelMap modelo= new ModelMap();
			modelo.put("token", token);
			return new ModelAndView("formularioCambiarClaveUsuario",modelo);
		}else{
			PasswordResetToken t=servicioVerificarToken.recuperarUsuarioConToken(token);
			ModelMap modelo= new ModelMap();
			modelo.put("token", t);
			modelo.put("error", "Token expirado");
			return new ModelAndView("vistaTokenExpirado",modelo);
		}
	}
	
	@RequestMapping(path="/cambiar-clave", method = RequestMethod.POST)
	public ModelAndView cambiarClave(@ModelAttribute ("token") String token,
									 @ModelAttribute ("password") String password){
		servicioCambiarClave.cambiarClave(token, password);
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
}