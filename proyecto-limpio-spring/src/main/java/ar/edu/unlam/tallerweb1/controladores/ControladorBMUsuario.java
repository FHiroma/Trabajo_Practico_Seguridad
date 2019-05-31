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
		model.put("password", "Actualizacion de contrase�a exitosa, Ingrese su usuario y su nueva clave");
		
		return new ModelAndView("login", model);
	}
	
//	@RequestMapping("/crear-texto")
//	public ModelAndView crearTexto(){
//		
//	
//			 
//			  final String inputFilePath = "C:/Users/gabri/eclipse-mars-workspace/Trabajo_Practico_Seguridad/proyecto-limpio-spring/store-file/output.txt";
//			 
//			  JFrame jFrame = new JFrame("Load ,Edit and Save file");
//			 
//			  Container content = jFrame.getContentPane();
//			 
//			  final JEditorPane edPane = new JEditorPane();
//			 
//			  JScrollPane sPne = new JScrollPane(edPane);
//			 
//			  content.add(sPne, BorderLayout.CENTER);
//			 
//			  edPane.setEditorKit(new HTMLEditorKit());
//			 
//			  JPanel jPanel = new JPanel();
//			 
//			  Action Load = new AbstractAction() {
//			 
//			@Override
//			 
//			public void actionPerformed(ActionEvent event) {
//			 
//			    try {
//			 
//			  load(edPane, inputFilePath);
//			 
//			    } catch (Exception e1) {
//			 
//			  e1.printStackTrace();
//			 
//			    }
//			 
//			}
//			 
//			  };
//			 
//			  Load.putValue(Action.NAME, "Load");
//			 
//			  JButton loadButton = new JButton(Load);
//			 
//			  jPanel.add(loadButton);
//			 
//			  Action absActionSave = new AbstractAction() {
//			 
//			@Override
//			 
//			public void actionPerformed(ActionEvent event) {
//			 
//			    try {
//			 
//			  save(edPane, inputFilePath);
//			 
//			    } catch (Exception e1) {
//			 
//			  e1.printStackTrace();
//			 
//			    }
//			 
//			}
//			 
//			  };
//			 
//			  absActionSave.putValue(Action.NAME, "Save");
//			 
//			  JButton jButton = new JButton(absActionSave);
//			 
//			  jPanel.add(jButton);
//			 
//			  Action absActionClear = new AbstractAction() {
//			 
//			@Override
//			 
//			public void actionPerformed(ActionEvent event) {
//			 
//			    edPane.setText("");
//			 
//			}
//			 
//			  };
//			 
//			  absActionClear.putValue(Action.NAME, "Clear");
//			 
//			  JButton clearButton = new JButton(absActionClear);
//			 
//			  jPanel.add(clearButton);
//			 
//			  content.add(jPanel, BorderLayout.SOUTH);
//			 
//			  jFrame.setSize(800, 600);
//			 
//			  jFrame.setVisible(true);
//			    
//			 
//			  public static void save(JTextComponent text, String inputFile) throws Exception {
//			 
//			  FileWriter writer = null;
//			 
//			  writer = new FileWriter(inputFile);
//			 
//			  text.write(writer);
//			 
//			  writer.close();
//			    }
//			 
//			    public static void load(JTextComponent text, String inputFile) throws Exception {
//			 
//			  FileReader inputReader = null;
//			 
//			  inputReader = new FileReader(inputFile);
//			 
//			  text.read(inputReader, inputFile);
//			 
//			  inputReader.close();
//			 
//			    }
//			}
//
//	protected void save(JEditorPane edPane, String inputFilePath) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	protected void load(JEditorPane edPane, String inputFilePath) {
//		// TODO Auto-generated method stub
//		
//	}

}









