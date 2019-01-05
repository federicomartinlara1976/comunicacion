package net.bounceme.chronos.comunicacion.helpers;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.config.AppConfig;

@Component
public class EmailHelper {
	
	@Autowired
	@Qualifier(AppConfig.TLS_MAIL_SESSION)
	private Session session;	

	/**
	 * Utility method to send simple HTML email
	 * 
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 */
	public void sendEmail(String toEmail, String subject, String body) throws MessagingException, UnsupportedEncodingException {
		MimeMessage msg = new MimeMessage(session);
		// set message headers
		msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		msg.addHeader("format", "flowed");
		msg.addHeader("Content-Transfer-Encoding", "8bit");

		msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));

		msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

		msg.setSubject(subject, "UTF-8");

		msg.setText(body, "UTF-8");

		msg.setSentDate(new Date());

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
		Transport.send(msg);
	}
}
