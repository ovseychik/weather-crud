package kz.test.weathercrud.model.dto.weatherapi.response;

import lombok.Data;

@Data
public class WeatherResponse {

  private Location location;
  private Current current;
}
