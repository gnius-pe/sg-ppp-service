package com.servicios.sppp.back_end_sppp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SG-PPP API")
                        .version("1.0.0")
                        .description("Sistema de Gestión de Prácticas Pre-profesionales")
                        .contact(new Contact().name("SG-PPP Team")));
    }
}