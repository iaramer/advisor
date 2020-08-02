package ai.adv.portfoliomanager.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ModelSimplePositionDto {

  private UUID uuid;

  private UUID portfolioUuid;

  private UUID parentComplexPositionUuid;

  private String ticker;

  private int size;
}
