package ai.adv.portfoliomanager.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ClientSimplePositionDto {

  private UUID uuid;

  private UUID portfolioUuid;

  private UUID parentComplexPositionUuid;

  private String ticker;

  private BigDecimal share;
}
