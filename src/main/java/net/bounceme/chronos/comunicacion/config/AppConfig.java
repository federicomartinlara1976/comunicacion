package net.bounceme.chronos.comunicacion.config;

import java.text.SimpleDateFormat;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.comunicacion.jms.Receiver;
import net.bounceme.chronos.comunicacion.utils.Constantes;

@Configuration
@Slf4j
public class AppConfig {
	
	public static final String QUEUE_NAME = "notificaciones";
	
	public static final String TOPIC_NAME = "spring-boot-exchange";
	
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
}
