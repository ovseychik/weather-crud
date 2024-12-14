package kz.test.weathercrud.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Schema(description = "Request object for creating a new city")
public class CreateCityRequest {

  @Schema(description = "City name", example = "Almaty")
  private String name;

  @Schema(description = "Country code", example = "KZ")
  private String country;

  @Schema(description = "Latitude", example = "43.14")
  private BigDecimal latitude;

  @Schema(description = "Longitude", example = "76.54")
  private BigDecimal longitude;
}
