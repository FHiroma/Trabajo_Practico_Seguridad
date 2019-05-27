package ar.edu.unlam.tallerweb1.controladores;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
 
import java.beans.PropertyEditorSupport;
 
@ControllerAdvice
public class CleanStringAdvice {
	static public class StringCleaner extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) {
			if (text == null) {
				setValue(null);
			}
			else {
				String safe = Jsoup.clean(text, Whitelist.simpleText());
				setValue(safe);
			}
		}
 
	}
 
	@InitBinder
	public void bindStringCleaner(WebDataBinder webDataBinder) {
		StringCleaner stringCleaner = new StringCleaner();
		webDataBinder.registerCustomEditor(String.class, stringCleaner);
	}

}