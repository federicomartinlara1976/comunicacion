package net.bounceme.chronos.comunicacion.swagger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>(
			Arrays.asList("application/json", "application/xml"));
	
	@Value("${swagger.contact.name}")
	private String contactName;
	
	@Value("${swagger.contact.web}")
	private String contactWeb;
	
	@Value("${swagger.contact.email}")
	private String contactEmail;
	
	@Value("${swagger.app.name}")
	private String appName;
	
	@Value("${swagger.app.description}")
	private String appDesc;
	
	@Value("${swagger.app.version}")
	private String appVersion;
	
	@Value("${swagger.app.license}")
	private String appLicense;
	
	@Value("${swagger.app.urlLicense}")
	private String appUrlLicense;
	
	private Contact defaultContact() {
		return new Contact(contactName, contactWeb, contactEmail);
	}
	
	private ApiInfo defaultApiInfo() {
		return new ApiInfo(appName, appDesc, appVersion,
				"urn:tos", defaultContact(), appLicense, appUrlLicense);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(defaultApiInfo()).produces(DEFAULT_PRODUCES_AND_CONSUMES)
				.consumes(DEFAULT_PRODUCES_AND_CONSUMES);
	}
}