package kz.test.weathercrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WeatherCrudApplication {

  public static void main(String[] args) {
    SpringApplication.run(WeatherCrudApplication.class, args);
  }
}
