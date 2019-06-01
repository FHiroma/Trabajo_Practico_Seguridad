package ar.edu.unlam.tallerweb1.controladores;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioRegistrarUsuario;

@Controller
public class ControladorRegistrarUsuario {
	
	@Inject
	private ServicioRegistrarUsuario servicioRegistrarUsuario;
	
	@RequestMapping(path="registrar-usuario", method= RequestMethod.POST)
	public ModelAndView insertarUsuario(@ModelAttribute("usuario") Usuario usuario){
		boolean validarPass = servicioRegistrarUsuario.registrarUsuario(usuario);
		String mensaje="";
		ModelMap model = new ModelMap();
		
		if(validarPass){
			return new ModelAndView("registroExitoso",model);
		}else{
			mensaje="Revise sus datos, no cumplen con nuestras politicas de seguridad";
			model.put("mensaje", mensaje);
			return new ModelAndView("registrarUsuario", model);
		}
			
	}
}
