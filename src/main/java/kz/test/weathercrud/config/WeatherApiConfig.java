package kz.test.weathercrud.config;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import kz.test.weathercrud.client.util.WeatherApiErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableFeignClients
@Import(FeignConfig.class)
public class WeatherApiConfig {

  @Bean
  public RequestInterceptor apiKeyInterceptor(@Value("${weatherapi.key}") String apiKey) {
    return requestTemplate ->
        requestTemplate.query("key", apiKey);
  }

  @Bean
  public ErrorDecoder weatherApiErrorDecoder() {
    return new WeatherApiErrorDecoder();
  }
}
