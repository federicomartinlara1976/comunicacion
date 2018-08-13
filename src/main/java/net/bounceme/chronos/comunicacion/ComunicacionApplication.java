package net.bounceme.chronos.comunicacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase que inicia la aplicaciÃ³n y configura los repositoriuos y algunos beans
 * 
 * @author frederik
 *
 */
@SpringBootApplication
public class ComunicacionApplication {

	/**
	 * Método de arranque de la aplicación
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ComunicacionApplication.class, args);
	}
}
