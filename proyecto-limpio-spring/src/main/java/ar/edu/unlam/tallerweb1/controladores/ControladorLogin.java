package ar.edu.unlam.tallerweb1.controladores;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioLog;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;
import ar.edu.unlam.tallerweb1.servicios.ServicioRegistrarUsuario;

@Controller
public class ControladorLogin {

	// La anotacion @Inject indica a Spring que en este atributo se debe setear (inyeccion de dependencias)
	// un objeto de una clase que implemente la interface ServicioLogin, dicha clase debe estar anotada como
	// @Service o @Repository y debe estar en un paquete de los indicados en applicationContext.xml
	@Inject
	private ServicioLogin servicioLogin;
	@Inject
	private ServicioRegistrarUsuario servicioRegistrarUsuario;
	@Inject
	private ServicioLog servicioLog;
	

	// Este metodo escucha la URL localhost:8080/NOMBRE_APP/login si la misma es invocada por metodo http GET
	@RequestMapping("/login")
	public ModelAndView irALogin() {

		ModelMap modelo = new ModelMap();
		// Se agrega al modelo un objeto del tipo Usuario con key 'usuario' para que el mismo sea asociado
		// al model attribute del form que esta definido en la vista 'login'
		Usuario usuario = new Usuario();
		modelo.put("usuario", usuario);
		// Se va a la vista login (el nombre completo de la lista se resuelve utilizando el view resolver definido en el archivo spring-servlet.xml)
		// y se envian los datos a la misma  dentro del modelo
		return new ModelAndView("login", modelo);
	}

	// Este metodo escucha la URL validar-login siempre y cuando se invoque con metodo http POST
	// El método recibe un objeto Usuario el que tiene los datos ingresados en el form correspondiente y se corresponde con el modelAttribute definido en el
	// tag form:form
	@RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        // invoca el metodo consultarUsuario del servicio y hace un redirect a la URL /home, esto es, en lugar de enviar a una vista
        // hace una llamada a otro action a través de la URL correspondiente a ésta
        Usuario usuarioBuscado = servicioLogin.consultarUsuario(usuario);
        if (usuarioBuscado != null) {

            if(usuarioBuscado.getEstado().equals(true)){
            request.getSession().setAttribute("id", usuarioBuscado.getId());
            model.put("sesion", request);
//            return new ModelAndView("redirect:/home");
        if("user".equals(usuarioBuscado.getRol())){
            servicioLog.guardarRegistro("login", usuarioBuscado.getId());
            return new ModelAndView("redirect:/homeUser",model);
            }
        if("admin".equals(usuarioBuscado.getRol())){
            return new ModelAndView("redirect:/homeAdmin");
            }
        } 
            else {
                // si el usuario no existe agrega un mensaje de error en el modelo.
                model.put("error", "Estado de usuario:Inhabilitado");
            }
            }
            else {
            // si el usuario no existe agrega un mensaje de error en el modelo.
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("login", model);
    }

	// Escucha la URL /home por GET, y redirige a una vista.
	@RequestMapping(path = "/homeUser", method = RequestMethod.GET)
	public ModelAndView irAHomeUser() {
		return new ModelAndView("homeUser");
	}
	
	@RequestMapping(path = "/homeAdmin", method = RequestMethod.GET)
	public ModelAndView irAHomeAdmin(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		if (session == null) {
		    return new ModelAndView("redirect:/login");
		}
		
		return new ModelAndView("homeAdmin");
	}

	// Escucha la url /, y redirige a la URL /login, es lo mismo que si se invoca la url /login directamente.
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView inicio() {
		return new ModelAndView("redirect:/login");
	}
	
	@RequestMapping("/registro")
	public ModelAndView registrarUsuario(){	
		ModelMap modelo = new ModelMap();
		Usuario usuario = new Usuario();
		modelo.put("usuario", usuario);
		return new ModelAndView("registrarUsuario", modelo);
	}
	
	@RequestMapping(path ="/registrar-usuario", method = RequestMethod.POST)
		public ModelAndView insertarUsuario(@ModelAttribute("usuario") Usuario usuario){
		ModelMap modelo= new ModelMap();
		servicioRegistrarUsuario.registrarUsuario(usuario);
		return new ModelAndView("registroExitoso",modelo);
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request){
		servicioLog.guardarRegistro("logout", (Long)request.getSession().getAttribute("id"));
		HttpSession session = request.getSession();
		session.invalidate();
		return new ModelAndView("redirect:/login");
	}	

}
	