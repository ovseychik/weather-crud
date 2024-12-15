package kz.test.weathercrud.model.dto.weatherapi.response;

import lombok.Data;

@Data
public class WeatherApiError {

  private Integer code;
  private String message;
  private String error;
}
