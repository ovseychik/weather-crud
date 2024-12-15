package kz.test.weathercrud.service.weather;

import java.math.BigDecimal;
import kz.test.weathercrud.model.dto.weatherapi.response.ForecastResponse;
import kz.test.weathercrud.model.dto.weatherapi.response.WeatherResponse;

public interface WeatherService {

  WeatherResponse getCurrentWeather(Long cityId, BigDecimal latitude, BigDecimal longitude);

  ForecastResponse getForecast(Long cityId, BigDecimal latitude, BigDecimal longitude, Integer days);
}
