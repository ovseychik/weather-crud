package kz.test.weathercrud.model.dto.weatherapi.response;

import lombok.Data;

@Data
public class ForecastResponse {

  private Location location;
  private Current current;
  private Forecast forecast;
}
