package kz.test.weathercrud.service.weather;

import kz.test.weathercrud.model.dto.weatherapi.response.ForecastResponse;
import kz.test.weathercrud.model.dto.weatherapi.response.WeatherResponse;
import reactor.core.publisher.Mono;

public interface WeatherService {

  public Mono<WeatherResponse> getCurrentWeather(Long cityId, Double latitude, Double longitude);

  public Mono<ForecastResponse> getForecast(Long cityId, Double latitude, Double longitude, Integer days);
}
