package kz.test.weathercrud.client;

import kz.test.weathercrud.config.WeatherApiConfig;
import kz.test.weathercrud.model.dto.weatherapi.response.ForecastResponse;
import kz.test.weathercrud.model.dto.weatherapi.response.WeatherResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(
    name = "weather-api",
    url = "${weatherapi.base-url}",
    configuration = WeatherApiConfig.class
)
public interface WeatherApiClient {

  @GetMapping("/current.json")
  Mono<WeatherResponse> getCurrentWeather(@RequestParam("q") String location);

  @GetMapping("/forecast.json")
  Mono<ForecastResponse> getForecast(
      @RequestParam("q") String location,
      @RequestParam("days") Integer days,
      @RequestParam("aqi") String aqi,
      @RequestParam("alerts") String alerts
  );
}
