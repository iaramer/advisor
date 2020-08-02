package ai.adv.portfoliomanager.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ComplexPositionDto {

  private UUID uuid;

  private UUID portfolioUuid;

  private UUID parentComplexPositionUuid;

  private String description;
}
