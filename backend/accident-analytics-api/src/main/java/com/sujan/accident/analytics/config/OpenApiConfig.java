package com.sujan.accident.analytics.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI accidentAnalyticsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("German Accident Analytics API")
                        .description("""
                                API for integrated Unfallatlas and Regionalatlas datasets.
                                Provides counts, filters, summaries, rankings, and provenance metadata.
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Sujan Karna")
                                .email("karna.sujan52@gmail.com"))
                )
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Local development server"));
    }
}
