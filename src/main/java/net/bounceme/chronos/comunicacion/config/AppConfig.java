package net.bounceme.chronos.comunicacion.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.comunicacion.dto.TipoComunicacionDTO;
import net.bounceme.chronos.comunicacion.jms.Receiver;
import net.bounceme.chronos.comunicacion.services.TiposComunicacionService;
import net.bounceme.chronos.comunicacion.utils.Constantes;

@Configuration
@Slf4j
public class AppConfig {
	
	public static final String QUEUE_NAME = "notificaciones";
	
	public static final String TOPIC_NAME = "spring-boot-exchange";
	
	public static final String TLS_MAIL_SESSION = "tls-mail-session";
	
	@Value("${application.envio.smtp.server}")
	String smtpServer;
	
	@Value("${application.envio.smtp.port}")
	String smtpPort;
	
	@Value("${application.envio.smtp.user}")
	String fromEmail;
	
	@Value("${application.envio.smtp.password}")
	String password;
	
	@Bean
	@Scope("prototype")
	public SimpleDateFormat dateFormat() {
		return new SimpleDateFormat(Constantes.DATE_FORMAT);
	}
	
	/**
	 * @return
	 */
	@Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }
	
	@Bean
    public TopicExchange exchange() {
        return new TopicExchange(TOPIC_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
	
	/**
	 * Este método alimenta la base de datos con datos iniciales, en concreto la
	 * tabla de tipos de comunicaciones. Si hay un fallo, termina el arranque de la
	 * aplicación con error
	 * 
	 * @param tipoComunicacionRepository
	 * @return
	 */
	@Bean
	public CommandLineRunner init(
			@Autowired TiposComunicacionService tiposComunicacionService) {

		Map<String, String> medios = new HashMap<>();
		medios.put("SMS", "SMS_Emisor");
		medios.put("FAX", "FAX_Emisor");
		medios.put("EMAIL", "EMAIL_Emisor");

		return evt -> Arrays.asList("SMS", "FAX", "EMAIL").forEach(a -> {
			try {
				String emisor = medios.get(a);
				
				if (Objects.isNull(tiposComunicacionService.findByName(a))) {
					TipoComunicacionDTO tipoComunicacionDTO = TipoComunicacionDTO.builder()
							.denominacion(a)
							.nombreClaseEmisora(emisor)
							.build();
					tiposComunicacionService.save(tipoComunicacionDTO);

				}
			} catch (Exception e) {
				log.error("ERROR: ", e);
			}
		});
	}
}
