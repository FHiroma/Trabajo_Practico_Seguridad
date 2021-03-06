package ar.edu.unlam.tallerweb1.controladores;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Log;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioAdmin;
import ar.edu.unlam.tallerweb1.servicios.ServicioBMUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioLog;

@Controller
public class ControladorAdmin {
	
	final static Logger logger = Logger.getLogger(ControladorAdmin.class); 
	
	@Inject
	private ServicioAdmin servicioAdmin;
	@Inject 
	private ServicioLog servicioLog;
	@Inject
	private ServicioAdmin servicioLeerTxt;
	@Inject
	private ServicioBMUsuario servicioRecuperarUsuarioConId;
	

	@RequestMapping("/listadoDeUsuarios")
	public ModelAndView irAListadoDeUsuarios() {
		ModelMap modelo = new ModelMap();
		List<Usuario> lista = servicioAdmin.traerListadoDeUsuarios();
		modelo.put("lista", lista);
		return new ModelAndView("listaDeUsuarios",modelo);
	}
	
	@RequestMapping(path="/activar-usuario")
	public ModelAndView activarAUnUsuario(@RequestParam ("id") Long id){
		ModelMap modelo = new ModelMap();
		String email = servicioAdmin.activarUsuario(id);
		Usuario usuario= servicioRecuperarUsuarioConId.recuperarUsuarioId(id);
		logger.info("Usuario activado:" + usuario.toString());
		modelo.put("mensaje1", email);
		List<Usuario> lista = servicioAdmin.traerListadoDeUsuarios();
		modelo.put("lista", lista);
		return new ModelAndView("listaDeUsuarios",modelo);
	}
	@RequestMapping(path="/desactivar-usuario")
	public ModelAndView desactivarAUnUsuario(@RequestParam ("id") Long id){
		ModelMap modelo = new ModelMap();
		String email = servicioAdmin.desactivarUsuario(id);
		Usuario usuario= servicioRecuperarUsuarioConId.recuperarUsuarioId(id);
		logger.info("Usuario desactivado:" + usuario.toString());
		modelo.put("mensaje2", email);
		List<Usuario> lista = servicioAdmin.traerListadoDeUsuarios();
		modelo.put("lista", lista);
		return new ModelAndView("listaDeUsuarios",modelo);
	}
	
	@SuppressWarnings("null")
	@RequestMapping(path="/ver-historial")
	public ModelAndView traerHistorialDelUsuario(@RequestParam ("id") Long id,HttpServletRequest request){
		String rol=(String)request.getSession().getAttribute("rol");
		HttpSession session = request.getSession();
		if (session == null) {
			session.invalidate();
		    return new ModelAndView("redirect:/login");
		}
		if(!"admin".equals(rol)){
			return new ModelAndView("redirect:/homeUser");
		}
		ModelMap modelo = new ModelMap();
		List<Log> listado = servicioLog.traerRegistrosDelUsuario(id);
		modelo.put("lista", listado);
		return new ModelAndView("historialDeActividad",modelo);
	}
	
	@RequestMapping(path="/leer-file/{id}")
	public ModelAndView leerFileTxt(@RequestParam ("id") Long id) {
	ModelMap modelo = new ModelMap();		
	List<Usuario> lista = servicioAdmin.traerListadoDeUsuarios();
	modelo.put("lista", lista);
	StringBuilder sb = new StringBuilder();
	servicioLeerTxt.leerTxt(sb,id);
	modelo.put("texto", sb);
	return new ModelAndView("leer-file",modelo);
	} 
}
