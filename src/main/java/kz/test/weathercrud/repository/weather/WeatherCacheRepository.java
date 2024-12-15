package kz.test.weathercrud.repository.weather;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.Duration;
import java.util.Optional;

public interface WeatherCacheRepository {

  Optional<JsonNode> findValidCache(Long cityId, String weatherType);

  void saveCache(Long cityId, String weatherType, JsonNode responseData, Duration ttl);

  void cleanupExpiredCache();
}
