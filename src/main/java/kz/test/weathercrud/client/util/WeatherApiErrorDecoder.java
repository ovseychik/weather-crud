package kz.test.weathercrud.client.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import io.micrometer.core.instrument.util.IOUtils;
import java.io.IOException;
import kz.test.weathercrud.model.dto.weatherapi.response.WeatherApiError;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeatherApiErrorDecoder implements ErrorDecoder {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public Exception decode(String methodKey, Response response) {
    try {
      String responseBody = IOUtils.toString(response.body().asInputStream());
      WeatherApiError error = objectMapper.readValue(responseBody, WeatherApiError.class);

      log.error("Weather API error: code={}, message={}", error.getCode(), error.getMessage());
      return new WeatherApiException(error.getCode(), error.getMessage());
    } catch (IOException e) {
      log.error("Failed to decode error response", e);
      return new WeatherApiException(response.status(), "Unknown error occurred");
    }
  }
}
