package kz.test.weathercrud.service.weather;

import kz.test.weathercrud.model.dto.weatherapi.response.ForecastResponse;
import kz.test.weathercrud.model.dto.weatherapi.response.WeatherResponse;

public interface WeatherService {

  public WeatherResponse getCurrentWeather(Long cityId, Double latitude, Double longitude);

  public ForecastResponse getForecast(Long cityId, Double latitude, Double longitude, Integer days);
}
