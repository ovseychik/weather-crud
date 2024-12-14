package kz.test.weathercrud.model.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {

  private Long id;
  private String name;
  private String country;
  private BigDecimal latitude;
  private BigDecimal longitude;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
}
