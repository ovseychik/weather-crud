package kz.test.weathercrud.model.dto.weatherapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Location {

  private String name;
  private String region;
  private String country;
  private Double lat;
  private Double lon;

  @JsonProperty("tz_id")
  private String tzId;

  @JsonProperty("localtime_epoch")
  private Long localtimeEpoch;

  private String localtime;
}
