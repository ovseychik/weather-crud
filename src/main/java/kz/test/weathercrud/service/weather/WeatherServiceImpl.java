package kz.test.weathercrud.service.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.Duration;
import kz.test.weathercrud.client.WeatherApiClient;
import kz.test.weathercrud.model.dto.weatherapi.response.ForecastResponse;
import kz.test.weathercrud.model.dto.weatherapi.response.WeatherResponse;
import kz.test.weathercrud.repository.weather.WeatherCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

  private static final Duration CURRENT_WEATHER_TTL = Duration.ofMinutes(30);
  private static final Duration FORECAST_TTL = Duration.ofHours(6);

  private final WeatherApiClient weatherApiClient;
  private final WeatherCacheRepository cacheRepository;
  private final ObjectMapper objectMapper;

  public WeatherResponse getCurrentWeather(Long cityId, BigDecimal latitude, BigDecimal longitude) {
    var cached = cacheRepository.findValidCache(cityId, "current");
    if (cached.isPresent()) {
      return objectMapper.convertValue(cached, WeatherResponse.class);
    }
    return fetchAndCacheCurrentWeather(cityId, latitude, longitude);
  }

  private WeatherResponse fetchAndCacheCurrentWeather(Long cityId, BigDecimal latitude, BigDecimal longitude) {
    String location = String.format("%f,%f", latitude, longitude);
    WeatherResponse response = weatherApiClient.getCurrentWeather(location);
    try {
      JsonNode jsonNode = objectMapper.valueToTree(response);
      cacheRepository.saveCache(cityId, "current", jsonNode, CURRENT_WEATHER_TTL);
    } catch (Exception e) {
      log.error("Failed to cache weather data", e);
    }
    return response;
  }

  public ForecastResponse getForecast(Long cityId, BigDecimal latitude, BigDecimal longitude, Integer days) {
    var cached = cacheRepository.findValidCache(cityId, "forecast_" + days);
    if (cached.isPresent()) {
      return objectMapper.convertValue(cached, ForecastResponse.class);
    }
    return fetchAndCacheForecast(cityId, latitude, longitude, days);
  }

  private ForecastResponse fetchAndCacheForecast(Long cityId, BigDecimal latitude, BigDecimal longitude, Integer days) {
    String location = String.format("%f,%f", latitude, longitude);
    ForecastResponse response = weatherApiClient.getForecast(location, days, "no", "no");
    try {
      JsonNode jsonNode = objectMapper.valueToTree(response);
      cacheRepository.saveCache(cityId, "forecast_" + days, jsonNode, FORECAST_TTL);
    } catch (Exception e) {
      log.error("Failed to cache forecast data", e);
    }
    return response;
  }
}
