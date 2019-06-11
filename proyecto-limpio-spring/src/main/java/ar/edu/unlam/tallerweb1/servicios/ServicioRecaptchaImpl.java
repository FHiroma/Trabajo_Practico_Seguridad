package ar.edu.unlam.tallerweb1.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service("servicioRecaptcha")
public class ServicioRecaptchaImpl implements ServicioRecaptcha{

	@Override
	public boolean checkRecaptcha(HttpServletRequest request) {
		String recap = request.getParameter("g-recaptcha-response");

		try{
			String urlGoogle = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s";
			String secret = "6Ld39acUAAAAAKcCSpsEIMWQnVxgN5qge4OgMFMw";
			// Send get request to Google reCaptcha server with secret key
			String urlFormatada = String.format(urlGoogle, secret, recap,
					(request.getRemoteAddr() != null ? request.getRemoteAddr() : "0.0.0.0"));
			URL url = new URL(urlFormatada);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			String line, outputString = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				outputString += line;
			}
			// Convert response into Object
			CaptchaResponse capRes = new Gson().fromJson(outputString, CaptchaResponse.class);

			// Verify whether the input from Human or Robot
			if (capRes.isSuccess()) {
				// Input by Human
				return true;
			} else {
				// Input by Robot
				return false;
			}

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	private class CaptchaResponse {
		private boolean success;
 		private String[] errorCodes;

 		public boolean isSuccess() {
 			return success;
 		}

 		@SuppressWarnings("unused")
		public void setSuccess(boolean success) {
 			this.success = success;
 		}

 		@SuppressWarnings("unused")
		public String[] getErrorCodes() {
 			return errorCodes;
 		}

	 	@SuppressWarnings("unused")
		public void setErrorCodes(String[] errorCodes) {
 			this.errorCodes = errorCodes;
 		}

 	}	

}
