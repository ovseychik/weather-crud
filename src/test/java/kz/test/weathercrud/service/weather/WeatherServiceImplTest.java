package kz.test.weathercrud.service.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;
import kz.test.weathercrud.client.WeatherApiClient;
import kz.test.weathercrud.model.dto.weatherapi.response.WeatherResponse;
import kz.test.weathercrud.repository.weather.WeatherCacheRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class WeatherServiceImplTest {

  @Mock
  private WeatherApiClient weatherApiClient;

  @Mock
  private WeatherCacheRepository cacheRepository;

  @Mock
  private ObjectMapper objectMapper;

  @InjectMocks
  private WeatherServiceImpl weatherService;

  public WeatherServiceImplTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetCurrentWeather_FetchesAndCachesWeatherWhenNotCached() {
    Long cityId = 1L;
    BigDecimal latitude = new BigDecimal("50.12345");
    BigDecimal longitude = new BigDecimal("30.12345");
    WeatherResponse apiResponse = new WeatherResponse();
    JsonNode jsonNode = mock(JsonNode.class);

    when(cacheRepository.findValidCache(cityId, "current")).thenReturn(Optional.empty());
    when(weatherApiClient.getCurrentWeather(String.format("%f,%f", latitude, longitude))).thenReturn(apiResponse);
    when(objectMapper.valueToTree(apiResponse)).thenReturn(jsonNode);

    WeatherResponse result = weatherService.getCurrentWeather(cityId, latitude, longitude);

    assertEquals(apiResponse, result);
    verify(cacheRepository, times(1)).findValidCache(cityId, "current");
    verify(weatherApiClient, times(1)).getCurrentWeather(String.format("%f,%f", latitude, longitude));
    verify(objectMapper, times(1)).valueToTree(apiResponse);
    verify(cacheRepository, times(1)).saveCache(cityId, "current", jsonNode, Duration.ofMinutes(30));
  }

  @Test
  void testGetCurrentWeather_HandlesCacheSaveFailureGracefully() {
    Long cityId = 1L;
    BigDecimal latitude = new BigDecimal("50.12345");
    BigDecimal longitude = new BigDecimal("30.12345");
    WeatherResponse apiResponse = new WeatherResponse();

    when(cacheRepository.findValidCache(cityId, "current")).thenReturn(Optional.empty());
    when(weatherApiClient.getCurrentWeather(String.format("%f,%f", latitude, longitude))).thenReturn(apiResponse);
    when(objectMapper.valueToTree(apiResponse)).thenThrow(new RuntimeException("Error serializing response"));

    WeatherResponse result = weatherService.getCurrentWeather(cityId, latitude, longitude);

    assertEquals(apiResponse, result);
    verify(cacheRepository, times(1)).findValidCache(cityId, "current");
    verify(weatherApiClient, times(1)).getCurrentWeather(String.format("%f,%f", latitude, longitude));
    verify(objectMapper, times(1)).valueToTree(apiResponse);
    verify(cacheRepository, never()).saveCache(eq(cityId), eq("current"), any(), any());
  }
}
