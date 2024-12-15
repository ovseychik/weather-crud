package kz.test.weathercrud.model.dto.weatherapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ForecastDay {

  private String date;

  @JsonProperty("date_epoch")
  private Long dateEpoch;

  private Day day;
}
