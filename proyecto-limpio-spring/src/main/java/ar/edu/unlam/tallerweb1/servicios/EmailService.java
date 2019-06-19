package ar.edu.unlam.tallerweb1.servicios;

import java.io.File;

public interface EmailService {
 
	public void send(String to, String subject, String text);
	
	public void send(String to, String subject, String text, File... attachments);
 
}
