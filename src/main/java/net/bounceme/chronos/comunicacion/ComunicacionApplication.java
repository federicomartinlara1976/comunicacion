package net.bounceme.chronos.comunicacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Clase que inicia la aplicación y configura los repositoriuos y algunos beans
 * 
 * @author frederik
 *
 */
@SpringBootApplication
@EnableScheduling
public class ComunicacionApplication {

	/**
	 * M�todo de arranque de la aplicaci�n
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ComunicacionApplication.class, args);
	}
}
