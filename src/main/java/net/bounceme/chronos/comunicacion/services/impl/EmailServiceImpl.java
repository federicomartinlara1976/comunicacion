package net.bounceme.chronos.comunicacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.comunicacion.services.EmailService;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender emailSender;
	
	@Value("${spring.mail.username}")
	private String sender;
	
	@Override
	public Boolean sendSimpleMessage(String to, String subject, String text) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			
			message.setFrom(sender);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);
			
			emailSender.send(message);
			
			return Boolean.TRUE;
		} catch (Exception e) {
			log.error("Error: {}", e.getMessage());
			return Boolean.FALSE;
		}
	}

}
