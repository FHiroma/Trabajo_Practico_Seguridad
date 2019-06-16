package ar.edu.unlam.tallerweb1.servicios;

import javax.servlet.http.HttpServletRequest;

public interface ServicioRecaptcha {

	boolean checkRecaptcha(HttpServletRequest request);
}
