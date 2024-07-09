package org.website.thienan.ricewaterthienan.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Slf4j
@Profile({"dev","test"})
public class OpenAPISwaggerConfiguration {

    @Bean
    OpenAPI openAPI(@Value("${open.api.title}") String title,
                    @Value("${open.api.version}") String version,
                    @Value("${open.api.description}") String description,
                    @Value("${open.api.server}") String server) {
        log.info("Config Swagger open api");
        return new OpenAPI().info(new Info().title(title)
                .version(version)
                .description(description)
                .license(new License().name("Truong coder").url("https://github.com/truongnguyenfpthcm2605")))
                .servers(List.of(new Server().url(server).description(description)))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .security(List.of(new SecurityRequirement().addList("bearerAuth")));
    }

    @Bean
    GroupedOpenApi groupedOpenApi(){
        log.info("Config group scan packages API");
        return GroupedOpenApi.builder()
                .group("API-Service-thienan")
                .packagesToScan("org.website.thienan.ricewaterthienan.controller.apiv1")
                .build();
    }
}
