package com.greengrim.green.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        servers = {@Server(url = "https://www.greengrim.store", description = "Prod Server URL"),
                   @Server(url = "https://dev.greengrim.store", description = "Develop Server URL"),
                   @Server(url = "http://localhost:8080", description = "Local Server URL")} ,
        info = @Info(title = "GreenGrim Server's API",
        description = "그린그림 서버 API 명세서",
        version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi OpenApi() {
    String[] paths = {"/**"};

    return GroupedOpenApi.builder()
        .group("그린그림 API v1")
        .pathsToMatch(paths)
        .build();
  }
}
