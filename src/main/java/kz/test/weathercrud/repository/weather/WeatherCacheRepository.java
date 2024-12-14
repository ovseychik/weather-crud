package kz.test.weathercrud.repository.weather;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.Duration;
import java.util.Optional;

public interface WeatherCacheRepository {

  public Optional<JsonNode> findValidCache(Long cityId, String weatherType);

  public void saveCache(Long cityId, String weatherType, JsonNode responseData, Duration ttl);

  public void cleanupExpiredCache();
}
