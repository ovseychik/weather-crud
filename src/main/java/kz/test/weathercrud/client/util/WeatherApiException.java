package kz.test.weathercrud.client.util;

public class WeatherApiException extends RuntimeException {

  private final Integer code;
  private final String message;

  public WeatherApiException(Integer code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
