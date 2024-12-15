package kz.test.weathercrud.client;

import kz.test.weathercrud.config.WeatherApiConfig;
import kz.test.weathercrud.model.dto.weatherapi.response.ForecastResponse;
import kz.test.weathercrud.model.dto.weatherapi.response.WeatherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "weather-api-client",
    url = "${weatherapi.base-url}",
    configuration = WeatherApiConfig.class
)
public interface WeatherApiClient {

  @GetMapping("/current.json")
  WeatherResponse getCurrentWeather(@RequestParam("q") String location);

  @GetMapping("/forecast.json")
  ForecastResponse getForecast(
      @RequestParam("q") String location,
      @RequestParam("days") Integer days,
      @RequestParam("aqi") String aqi,
      @RequestParam("alerts") String alerts
  );
}
