package kz.test.weathercrud.model.dto.weatherapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class Forecast {

  @JsonProperty("forecastday")
  private List<ForecastDay> forecastDay;
}
