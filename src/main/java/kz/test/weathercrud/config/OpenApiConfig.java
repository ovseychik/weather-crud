package kz.test.weathercrud.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI weatherApiDocs() {
    return new OpenAPI()
        .info(new Info()
            .title("Weather Service API")
            .description("REST API for managing cities and weather information")
            .version("1.0.0"));
  }
}
