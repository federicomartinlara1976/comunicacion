package net.bounceme.chronos.comunicacion.config;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import net.bounceme.chronos.comunicacion.utils.Constantes;

@Configuration
public class AppConfig {
	
	@Bean
	@Scope("prototype")
	public SimpleDateFormat dateFormat() {
		return new SimpleDateFormat(Constantes.DATE_FORMAT);
	}
}
