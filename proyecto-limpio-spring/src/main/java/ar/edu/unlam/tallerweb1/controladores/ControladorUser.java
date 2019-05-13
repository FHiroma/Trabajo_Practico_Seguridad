package ar.edu.unlam.tallerweb1.controladores;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.servicios.ServicioLog;

@Controller
public class ControladorUser {
	@Inject
	private ServicioLog servicioLog;
	
	@RequestMapping(path="verHistorialDeActividad")
	public ModelAndView verHistorialDeActividad(){
		ModelMap modelo = new ModelMap();
		
		return new ModelAndView ("historialDeActividad",modelo);
	}
}
