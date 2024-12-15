package kz.test.weathercrud.repository.weather;

import static kz.test.weather.jooq.generated.Tables.WEATHER_CACHE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.JSONB;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class WeatherCacheRepositoryImpl implements WeatherCacheRepository {

  private final DSLContext dsl;

  public WeatherCacheRepositoryImpl(DSLContext dsl) {
    this.dsl = dsl;
  }

  public Optional<JsonNode> findValidCache(Long cityId, String weatherType) {
    return dsl.select(WEATHER_CACHE.RESPONSE_DATA)
        .from(WEATHER_CACHE)
        .where(WEATHER_CACHE.CITY_ID.eq(cityId))
        .and(WEATHER_CACHE.WEATHER_TYPE.eq(weatherType))
        .and(WEATHER_CACHE.EXPIRES_AT.greaterThan(OffsetDateTime.now()))
        .orderBy(WEATHER_CACHE.CREATED_AT.desc())
        .limit(1)
        .fetchOptional(WEATHER_CACHE.RESPONSE_DATA)
        .map(this::convertJsonNode);
  }

  public void saveCache(Long cityId, String weatherType, JsonNode responseData, Duration ttl) {
    dsl.insertInto(WEATHER_CACHE)
        .set(WEATHER_CACHE.CITY_ID, cityId)
        .set(WEATHER_CACHE.WEATHER_TYPE, weatherType)
        .set(WEATHER_CACHE.RESPONSE_DATA, convertJsonNode(responseData))
        .set(WEATHER_CACHE.EXPIRES_AT, OffsetDateTime.now().plus(ttl))
        .execute();
  }

  @Scheduled(cron = "0 0 * * * *")
  public void cleanupExpiredCache() {
    dsl.deleteFrom(WEATHER_CACHE)
        .where(WEATHER_CACHE.EXPIRES_AT.lessThan(OffsetDateTime.now()))
        .execute();
  }

  private JsonNode convertJsonNode(JSONB jsonb) {
    try {
      return new ObjectMapper().readTree(jsonb.data());
    } catch (JsonProcessingException e) {
      log.error("Error converting json to JsonNode", e);
    }
    return null;
  }

  private JSONB convertJsonNode(JsonNode json) {
    try {
      return JSONB.valueOf(new ObjectMapper().writeValueAsString(json));
    } catch (JsonProcessingException e) {
      log.error("Error converting JsonNode to JSONB", e);
      return null;
    }
  }
}
