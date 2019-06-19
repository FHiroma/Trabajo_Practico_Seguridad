package ar.edu.unlam.tallerweb1.servicios;
import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
 
/**
 * Servicio de envío de emails
 */
public class EmailServiceImpl implements EmailService {
 
	private static final Log log = LogFactory.getLog(EmailServiceImpl.class);
 
	/** wrapper de Spring sobre javax.mail */
	private JavaMailSenderImpl mailSender;
 
	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
 
	/** correo electrónico del remitente */
	private String from;
 
	public void setFrom(String from) {
		this.from = from;
	}
 
	public String getFrom() {
		return from;
	}
 
	/** flag para indicar si está activo el servicio */
	public boolean active = true;
 
	public boolean isActive() {
		return active;
	}
 
	public void setActive(boolean active) {
		this.active = active;
	}
 
	private static final File[] NO_ATTACHMENTS = null;
 
	/** envío de email 
	 * @param to correo electrónico del destinatario
	 * @param subject asunto del mensaje
	 * @param text cuerpo del mensaje
	 */
	public void send(String to, String subject, String text) {
		send(to, subject, text, NO_ATTACHMENTS);
	}
 
	/** envío de email con attachments
	 * @param to correo electrónico del destinatario
	 * @param subject asunto del mensaje
	 * @param text cuerpo del mensaje
	 * @param attachments ficheros que se anexarán al mensaje 
	 */
	public void send(String to, String subject, String text, File... attachments) {

		// asegurando la trazabilidad
		if (log.isDebugEnabled()) {
			final boolean usingPassword = !"".equals(mailSender.getPassword());
			log.debug("Sending email to: '" + to + "' [through host: '" + mailSender.getHost() + ":"
					+ mailSender.getPort() + "', username: '" + mailSender.getUsername() + "' usingPassword:"
					+ usingPassword + "].");
			log.debug("isActive: " + active);
		}
		// el servicio esta activo?
		if (!active) return;
 
		// plantilla para el envío de email
		final MimeMessage message = mailSender.createMimeMessage();
 
		try {
			// el flag a true indica que va a ser multipart
			final MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			// settings de los parámetros del envío
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setFrom(getFrom());
			helper.setText(text);
 
			// adjuntando los ficheros
			if (attachments != null) {
				for (int i = 0; i < attachments.length; i++) {
					FileSystemResource file = new FileSystemResource(attachments[i]);
					helper.addAttachment(attachments[i].getName(), file);
					if (log.isDebugEnabled()) {
						log.debug("File '" + file + "' attached.");
					}
				}
			}
 
		} catch (MessagingException e) {
			new RuntimeException(e);
		}
		
		// el envío
		this.mailSender.send(message);
	}
 
}