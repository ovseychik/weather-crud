package kz.test.weathercrud.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
@Schema(description = "City response object")
public class CityResponse {

  @Schema(description = "City ID", example = "1")
  private Long id;

  @Schema(description = "City name", example = "London")
  private String name;

  @Schema(description = "Country code", example = "GB")
  private String country;

  @Schema(description = "Latitude", example = "51.5074")
  private BigDecimal latitude;

  @Schema(description = "Longitude", example = "-0.1278")
  private BigDecimal longitude;

  @Schema(description = "Creation timestamp")
  private OffsetDateTime createdAt;

  @Schema(description = "Last update timestamp")
  private OffsetDateTime updatedAt;
}
