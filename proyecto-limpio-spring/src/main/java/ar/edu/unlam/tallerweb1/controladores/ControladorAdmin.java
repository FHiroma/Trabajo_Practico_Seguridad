package ar.edu.unlam.tallerweb1.controladores;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioAdmin;

@Controller
public class ControladorAdmin {
	@Inject
	private ServicioAdmin servicioAdmin;

	@RequestMapping("/listadoDeUsuarios")
	public ModelAndView irAListadoDeUsuarios() {
		ModelMap modelo = new ModelMap();
		List<Usuario> lista = servicioAdmin.traerListadoDeUsuarios();
		modelo.put("lista", lista);
		return new ModelAndView("listaDeUsuarios",modelo);
	}
}
