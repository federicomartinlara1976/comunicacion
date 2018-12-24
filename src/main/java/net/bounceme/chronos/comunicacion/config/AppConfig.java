package net.bounceme.chronos.comunicacion.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.catalina.connector.Connector;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

import net.bounceme.chronos.comunicacion.data.dao.DaoPersistence;
import net.bounceme.chronos.comunicacion.data.dao.DaoQueries;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.jms.Receiver;
import net.bounceme.chronos.comunicacion.model.Aviso;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.model.DireccionCliente;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;
import net.bounceme.chronos.comunicacion.model.Notificacion;
import net.bounceme.chronos.comunicacion.model.RegistroNotificacion;
import net.bounceme.chronos.comunicacion.model.TipoComunicacion;
import net.bounceme.chronos.comunicacion.services.TiposComunicacionService;

@Configuration
public class AppConfig {
	Logger log = Logger.getLogger(AppConfig.class);
	
	public static final String CLIENTE_REPOSITORY = "clienteRepository";
	
	public static final String TIPOS_COMUNICACION_REPOSITORY = "tiposComunicacionRepository";
	
	public static final String MEDIOS_COMUNICACION_CLIENTE_REPOSITORY = "mediosComunicacionClienteRepository";
	
	public static final String DIRECCIONES_CLIENTE_REPOSITORY = "direccionesClienteRepository";

	public static final String AVISOS_REPOSITORY = "avisosRepository";
	
	public static final String NOTIFICACIONES_REPOSITORY = "notificacionesRepository";
	
	public static final String REGISTRO_NOTIFICACIONES_REPOSITORY = "registroNotificacionesRepository";
	
	public static final String QUEUE_NAME = "notificaciones";
	
	public static final String TOPIC_NAME = "spring-boot-exchange";
	
	public static final String TLS_MAIL_SESSION = "tls-mail-session";
	
	@Value("${tomcat.ajp.port}")
	int ajpPort;

	@Value("${tomcat.ajp.remoteauthentication}")
	String remoteAuthentication;

	@Value("${tomcat.ajp.enabled}")
	boolean tomcatAjpEnabled;
	
	@Value("${envio.smtp.server}")
	String smtpServer;
	
	@Value("${envio.smtp.port}")
	String smtpPort;
	
	@Value("${envio.smtp.user}")
	String fromEmail;
	
	@Value("${envio.smtp.password}")
	String password;
	
	@Bean(name = DaoQueries.NAME)
	public DaoQueries daoQueries() {
		return new DaoQueries();
	}
	
	/**
	 * Crea el repositorio para la entidad Cliente
	 * 
	 * @return bean del repositorio
	 */
	@Bean(name = CLIENTE_REPOSITORY)
	public DaoPersistence<Cliente> clienteRepository() {
		return new DaoPersistence<>(Cliente.class);
	}
	
	/**
     * Crea el repositorio para la entidad de tipos de comunicación
     * (FAX, SMS, ...)
     * 
     * @return bean del repositorio
     */
	@Bean(name = TIPOS_COMUNICACION_REPOSITORY)
	public DaoPersistence<TipoComunicacion> tiposComunicacionRepository() {
		return new DaoPersistence<>(TipoComunicacion.class);
	}
	
	/**
     * Crea el repositorio para la entidad de medios de comunicación
     * del cliente (nº de móvil, FAX, email)
     * 
     * @return bean del repositorio
     */
	@Bean(name = MEDIOS_COMUNICACION_CLIENTE_REPOSITORY)
	public DaoPersistence<MedioComunicacionCliente> mediosComunicacionClienteRepository() {
		return new DaoPersistence<>(MedioComunicacionCliente.class);
	}
	
	/**
     * Crea el repositorio para la entidad de direcciones
     * del cliente 
     * 
     * @return bean del repositorio
     */
	@Bean(name = DIRECCIONES_CLIENTE_REPOSITORY)
	public DaoPersistence<DireccionCliente> direccionesClienteRepository() {
		return new DaoPersistence<>(DireccionCliente.class);
	}
	
	/**
     * Crea el repositorio para la gestión de los avisos
     * de inicio de obra del cliente
     * 
     * @return bean del repositorio
     */
	@Bean(name = AVISOS_REPOSITORY)
	public DaoPersistence<Aviso> avisosRepository() {
		return new DaoPersistence<>(Aviso.class);
	}
	
	/**
     * Crea el repositorio para las notificaciones a un cliente,
     * independientemente del medio que se use
     * 
     * @return bean del repositorio
     */
	@Bean(name = NOTIFICACIONES_REPOSITORY)
	public DaoPersistence<Notificacion> notificacionesRepository() {
		return new DaoPersistence<>(Notificacion.class);
	}
	
	/**
     * Crea el repositorio para el registro de las notificaciones,
     * independientemente del medio que se use
     * 
     * @return bean del repositorio
     */
	@Bean(name = REGISTRO_NOTIFICACIONES_REPOSITORY)
	public DaoPersistence<RegistroNotificacion> registroNotificacionesRepository() {
		return new DaoPersistence<>(RegistroNotificacion.class);
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
    
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {

        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        if (tomcatAjpEnabled)
        {
            Connector ajpConnector = new Connector("AJP/1.3");
            ajpConnector.setPort(ajpPort);
            ajpConnector.setSecure(false);
            ajpConnector.setAllowTrace(false);
            ajpConnector.setScheme("http");
            tomcat.addAdditionalTomcatConnectors(ajpConnector);
        }

        return tomcat;
    }
    
    /**
	 * Configuraci�n para el cross-origin
	 * 
	 * @return
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**");
			}
		};
	}
	
	/**
	 * Método que inicia el servidor wiremock
	 * 
	 * @return
	 */
	@Bean
	public WireMockClassRule wiremock() {
		return new WireMockClassRule(9100);
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
			@Autowired @Qualifier(TiposComunicacionService.NAME) TiposComunicacionService tiposComunicacionService) {

		Map<String, String> medios = new HashMap<>();
		medios.put("SMS", "SMS_Emisor");
		medios.put("FAX", "FAX_Emisor");
		medios.put("EMAIL", "EMAIL_Emisor");

		return evt -> Arrays.asList("SMS", "FAX", "EMAIL").forEach(a -> {
			try {
				String emisor = medios.get(a);
				
				if (Objects.isNull(tiposComunicacionService.get(a))) {
					tiposComunicacionService.nuevo(a, emisor);

				}
			} catch (ServiceException e) {
				log.error(e);
			}
		});
	}
	
	@Bean(name = TLS_MAIL_SESSION)
	Session tlsMailSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpServer); // SMTP Host
		props.put("mail.smtp.port", smtpPort); // TLS Port
		props.put("mail.smtp.auth", "true"); // enable authentication
		props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

		// create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
		    @Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		return Session.getInstance(props, auth);
	}
}
