package kz.test.weathercrud.service.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import kz.test.weathercrud.client.WeatherApiClient;
import kz.test.weathercrud.model.dto.weatherapi.response.ForecastResponse;
import kz.test.weathercrud.model.dto.weatherapi.response.WeatherResponse;
import kz.test.weathercrud.repository.weather.WeatherCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

  private static final Duration CURRENT_WEATHER_TTL = Duration.ofMinutes(30);
  private static final Duration FORECAST_TTL = Duration.ofHours(6);

  private final WeatherApiClient weatherApiClient;
  private final WeatherCacheRepository cacheRepository;
  private final ObjectMapper objectMapper;

  public Mono<WeatherResponse> getCurrentWeather(Long cityId, Double latitude, Double longitude) {
    return Mono.justOrEmpty(cacheRepository.findValidCache(cityId, "current"))
        .map(cached -> objectMapper.convertValue(cached, WeatherResponse.class))
        .switchIfEmpty(fetchAndCacheCurrentWeather(cityId, latitude, longitude));
  }

  private Mono<WeatherResponse> fetchAndCacheCurrentWeather(Long cityId, Double latitude, Double longitude) {
    String location = String.format("%f,%f", latitude, longitude);
    return weatherApiClient.getCurrentWeather(location)
        .doOnSuccess(response -> {
          try {
            JsonNode jsonNode = objectMapper.valueToTree(response);
            cacheRepository.saveCache(cityId, "current", jsonNode, CURRENT_WEATHER_TTL);
          } catch (Exception e) {
            log.error("Failed to cache weather data", e);
          }
        });
  }

  public Mono<ForecastResponse> getForecast(Long cityId, Double latitude, Double longitude, Integer days) {
    return Mono.justOrEmpty(cacheRepository.findValidCache(cityId, "forecast_" + days))
        .map(cached -> objectMapper.convertValue(cached, ForecastResponse.class))
        .switchIfEmpty(fetchAndCacheForecast(cityId, latitude, longitude, days));
  }

  private Mono<ForecastResponse> fetchAndCacheForecast(Long cityId, Double latitude, Double longitude, Integer days) {
    String location = String.format("%f,%f", latitude, longitude);
    return weatherApiClient.getForecast(location, days, "no", "no")
        .doOnSuccess(response -> {
          try {
            JsonNode jsonNode = objectMapper.valueToTree(response);
            cacheRepository.saveCache(cityId, "forecast_" + days, jsonNode, FORECAST_TTL);
          } catch (Exception e) {
            log.error("Failed to cache forecast data", e);
          }
        });
  }
}
