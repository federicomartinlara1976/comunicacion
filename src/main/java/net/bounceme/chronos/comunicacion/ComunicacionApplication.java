package net.bounceme.chronos.comunicacion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import net.bounceme.chronos.comunicacion.dao.DaoPersistence;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.jms.Receiver;
import net.bounceme.chronos.comunicacion.model.Aviso;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;
import net.bounceme.chronos.comunicacion.model.Notificacion;
import net.bounceme.chronos.comunicacion.model.TipoComunicacion;
import net.bounceme.chronos.comunicacion.services.TiposComunicacionService;

/**
 * Clase que inicia la aplicación y configura los repositoriuos y algunos beans
 * 
 * @author frederik
 *
 */
@SpringBootApplication
public class ComunicacionApplication {

	Logger log = Logger.getLogger(ComunicacionApplication.class);
	
	public static final String CLIENTE_REPOSITORY = "clienteRepository";
	
	public static final String TIPOS_COMUNICACION_REPOSITORY = "tiposComunicacionRepository";
	
	public static final String MEDIOS_COMUNICACION_CLIENTE_REPOSITORY = "mediosComunicacionClienteRepository";

	public static final String AVISOS_REPOSITORY = "avisosRepository";
	
	public static final String NOTIFICACIONES_REPOSITORY = "notificacionesRepository";
	
	public static final String QUEUE_NAME = "notificaciones";
	
	public static final String TOPIC_NAME = "spring-boot-exchange";
	
	/**
	 * Crea el repositorio para la entidad Cliente
	 * 
	 * @return bean del repositorio
	 */
	@Bean(name = CLIENTE_REPOSITORY)
	public DaoPersistence<Cliente> clienteRepository() {
		return new DaoPersistence<Cliente>(Cliente.class);
	}
	
	/**
     * Crea el repositorio para la entidad de tipos de comunicación
     * (FAX, SMS, ...)
     * 
     * @return bean del repositorio
     */
	@Bean(name = TIPOS_COMUNICACION_REPOSITORY)
	public DaoPersistence<TipoComunicacion> tiposComunicacionRepository() {
		return new DaoPersistence<TipoComunicacion>(TipoComunicacion.class);
	}
	
	/**
     * Crea el repositorio para la entidad de medios de comunicación
     * del cliente (nº de móvil, FAX, email)
     * 
     * @return bean del repositorio
     */
	@Bean(name = MEDIOS_COMUNICACION_CLIENTE_REPOSITORY)
	public DaoPersistence<MedioComunicacionCliente> mediosComunicacionClienteRepository() {
		return new DaoPersistence<MedioComunicacionCliente>(MedioComunicacionCliente.class);
	}
	
	/**
     * Crea el repositorio para la gestión de los avisos
     * de inicio de obra del cliente
     * 
     * @return bean del repositorio
     */
	@Bean(name = AVISOS_REPOSITORY)
	public DaoPersistence<Aviso> avisosRepository() {
		return new DaoPersistence<Aviso>(Aviso.class);
	}
	
	/**
     * Crea el repositorio para las notificaciones a un cliente,
     * independientemente del medio que se use
     * 
     * @return bean del repositorio
     */
	@Bean(name = NOTIFICACIONES_REPOSITORY)
	public DaoPersistence<Notificacion> notificacionesRepository() {
		return new DaoPersistence<Notificacion>(Notificacion.class);
	}
	
	/**
	 * @return
	 */
	@Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }
	
	@Bean
    TopicExchange exchange() {
        return new TopicExchange(TOPIC_NAME);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
	
	/**
	 * Este método alimenta la base de datos con datos iniciales, en concreto la tabla de tipos de comunicaciones.
	 * Si hay un fallo, termina el arranque de la aplicación con error
	 * 
	 * @param tipoComunicacionRepository
	 * @return
	 */
	@Bean
	CommandLineRunner init(
			@Autowired @Qualifier(TiposComunicacionService.NAME) TiposComunicacionService tiposComunicacionService) {

	    Map<String, String> medios = new HashMap<String, String>();
	    medios.put("SMS", "SMS_Emisor");
	    medios.put("FAX", "FAX_Emisor");
	    medios.put("EMAIL", "EMAIL_Emisor");
	    
		return (evt) -> Arrays.asList("SMS,FAX,EMAIL".split(",")).forEach(a -> {
			try {
			    String emisor = medios.get(a);
				tiposComunicacionService.nuevo(a, emisor);
			} catch (ServiceException e) {
				log.fatal(e);
				System.exit(1);
			}
		});
	}
	
	/**
	 * Configuración para el cross-origin
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
	 * Método de arranque de la aplicación
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ComunicacionApplication.class, args);
	}
}
