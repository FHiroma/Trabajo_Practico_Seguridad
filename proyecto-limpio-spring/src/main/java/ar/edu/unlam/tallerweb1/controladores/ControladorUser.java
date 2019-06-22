package ar.edu.unlam.tallerweb1.controladores;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Log;
import ar.edu.unlam.tallerweb1.servicios.ServicioLog;

@Controller
public class ControladorUser {
	
	
	@Inject
	private ServicioLog servicioLog;
	
	@RequestMapping(path="verHistorialDeActividad")
	public ModelAndView verHistorialDeActividad(HttpServletRequest request){
		ModelMap modelo = new ModelMap();
		System.out.println((Long)request.getSession().getAttribute("id"));
		List<Log> listado = servicioLog.traerRegistrosDelUsuario((Long)request.getSession().getAttribute("id"));
		modelo.put("lista", listado);
		return new ModelAndView ("historialDeActividad",modelo);
	}
}
