package ar.edu.unlam.tallerweb1.controladores;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioBMUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioLog;

@Controller
public class ControladorBMUsuario {
	
	@Inject
	private ServicioBMUsuario servicioModificacionUsuario;
	@Inject 
	private ServicioBMUsuario servicioRecuperarPassword;
	@Inject
	private ServicioLog servicioLog;
	
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
		model.put("password", "Actualizacion de contraseña exitosa, Ingrese su usuario y su nueva clave");
		
		return new ModelAndView("login", model);
	}
}









