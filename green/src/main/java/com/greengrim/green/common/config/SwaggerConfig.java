package com.greengrim.green.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "GreenGrim Server's API",
        description = "그린그림 서버 API 명세서",
        version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi OpenApi() {
    String[] paths = {"/api/**"};

    return GroupedOpenApi.builder()
        .group("그린그림 API v1")
        .pathsToMatch(paths)
        .build();
  }
}
