package org.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;



/**
 * Swagger/OpenAPI configuration for API documentation.
 * 
 * Provides interactive API documentation accessible at:
 * http://localhost:8080/swagger-ui.html
 * 
 * OpenAPI JSON available at:
 * http://localhost:8080/v3/api-docs
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mutant Detector API")
                        .version("1.0.0")
                        .description("""
                                API REST para detectar mutantes basándose en secuencias de ADN.
                                
                                Un humano es considerado mutante si su ADN contiene más de una secuencia
                                de cuatro letras iguales (A, T, C, G) en cualquier dirección:
                                - Horizontal (→)
                                - Vertical (↓)
                                - Diagonal (↘ ↙)
                                
                                **Endpoints disponibles:**
                                - POST /mutant - Verificar si un ADN es mutante
                                - GET /stats - Obtener estadísticas de verificaciones
                                """)
                        .contact(new Contact()
                                .name("MercadoLibre Challenge")
                                .email("support@example.com")
                                .url("https://github.com/tuusuario/mutant-detector"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Server"),
                        new Server()
                                .url("https://integradorv1.onrender.com")
                                .description("Production Server (Render)")
                ));
    }
}
