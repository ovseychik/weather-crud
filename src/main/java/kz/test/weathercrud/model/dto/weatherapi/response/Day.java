package kz.test.weathercrud.model.dto.weatherapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Day {

  @JsonProperty("maxtemp_c")
  private Double maxtempC;

  @JsonProperty("mintemp_c")
  private Double mintempC;

  @JsonProperty("avgtemp_c")
  private Double avgtempC;

  @JsonProperty("maxwind_kph")
  private Double maxwindKph;

  @JsonProperty("totalprecip_mm")
  private Double totalprecipMm;

  private Condition condition;
  private Double uv;
}
