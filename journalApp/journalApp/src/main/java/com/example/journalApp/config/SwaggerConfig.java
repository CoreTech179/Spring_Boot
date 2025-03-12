package com.example.journalApp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig{

    @Bean
    public OpenAPI myCustomConfiguration(){
        return new OpenAPI().info(
                new Info().title("Journal App APIs") // Title of the documentation
                        .description("Created using SpringBoot Framework") // API documentation description
        )
                .servers(
                        Arrays.asList(new Server().url("http://localhost:8080/journalApplication").description("local"),
                        new Server().url("http://localhost:8081/journalApplication").description("Live"))
                ) // We can add our own servers inside the documentation in this way.
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // Token Authentication
                .components(new Components().addSecuritySchemes(
                        "bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
//                        No need to memorize it is taken from official documentation as it
                ));

    }

}
