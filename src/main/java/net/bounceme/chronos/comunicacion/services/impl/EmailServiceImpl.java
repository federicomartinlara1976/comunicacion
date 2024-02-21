package net.bounceme.chronos.comunicacion.services.impl;

import java.util.Date;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.comunicacion.services.EmailService;
import net.bounceme.chronos.dto.mail.MailDataDTO;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${application.queue}")
	private String queueName;
	
	@Value("${spring.mail.username}")
	private String sender;
	
	@Override
	public Boolean sendSimpleMessage(String to, String subject, String text) {
		try {
			MailDataDTO message = MailDataDTO.builder()
					.sender(sender)
					.receiver(to)
					.subject(subject)
					.date(new Date())
					.message(text)
					.messageType("simple")
					.build();
			
			rabbitTemplate.convertAndSend(queueName, message);
			
			return Boolean.TRUE;
		} catch (Exception e) {
			log.error("Error: {}", e.getMessage());
			return Boolean.FALSE;
		}
	}

}
