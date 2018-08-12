package net.bounceme.chronos.comunicacion.swagger;

import io.swagger.annotations.Contact;
import io.swagger.annotations.ExternalDocs;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition(
        info = @Info(
                description = "Prueba de API para Tinsa",
                version = "V12.0.12",
                title = "Comunicacion",
                contact = @Contact(
                   name = "Federico Martín Lara", 
                   email = "federicomartinlara1976@gmail.com", 
                   url = "http://www.github.com/federicomartinlara1976"
                ),
                license = @License(
                   name = "Apache 2.0", 
                   url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        consumes = {"application/json", "application/xml"},
        produces = {"application/json", "application/xml"},
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
        externalDocs = @ExternalDocs(value = "Read This For Sure", url = "http://www.github.com/federicomartinlara1976/comunicacion")
)
public interface ApiDocumentationConfig {

}
