package net.bounceme.chronos.comunicacion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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

	/**
	 * Este método alimenta la base de datos con datos iniciales, en concreto la
	 * tabla de tipos de comunicaciones. Si hay un fallo, termina el arranque de
	 * la aplicación con error
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

		return (evt) -> Arrays.asList(new String[] { "SMS", "FAX", "EMAIL" }).forEach(a -> {
			String emisor = medios.get(a);
			tiposComunicacionService.nuevo(a, emisor);
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
