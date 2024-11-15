package com.security.basic.global.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @io.swagger.v3.oas.annotations.info.Info(
        title = "BASIC SECURITY API",
        version = "v1"
    ),
    servers = {
        @io.swagger.v3.oas.annotations.servers.Server(
            url = "http://localhost:9090/",
            description = "local test"
        )
    }
)
public class SwaggerConfiguration {

}
