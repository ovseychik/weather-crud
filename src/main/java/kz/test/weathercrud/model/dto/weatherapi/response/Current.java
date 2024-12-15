package kz.test.weathercrud.model.dto.weatherapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Current {

  @JsonProperty("last_updated_epoch")
  private Long lastUpdatedEpoch;

  @JsonProperty("last_updated")
  private String lastUpdated;

  @JsonProperty("temp_c")
  private Double tempC;

  @JsonProperty("is_day")
  private Integer isDay;

  @JsonProperty("wind_kph")
  private Double windKph;

  @JsonProperty("wind_degree")
  private Integer windDegree;

  @JsonProperty("wind_dir")
  private String windDir;

  @JsonProperty("precip_mm")
  private Double precipMm;

  @JsonProperty("precip_in")
  private Double precipIn;

  private Integer humidity;
  private Integer cloud;

  @JsonProperty("feelslike_c")
  private Double feelslikeC;

  @JsonProperty("dewpoint_c")
  private Double dewpointC;

  private Double uv;
}
