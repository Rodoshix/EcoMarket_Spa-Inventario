package com.ecomarket.inventario.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
            .info(new Info()
                .title("Inventario Service API - EcoMarket SPA")
                .version("1.0.0")
                .description("Documentación del microservicio de Inventario")
                .contact(new Contact().name("Equipo FullStack 1").email("equipo@ecomarket.cl")));
    }
}