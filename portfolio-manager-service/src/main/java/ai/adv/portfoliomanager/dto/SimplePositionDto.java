package ai.adv.portfoliomanager.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SimplePositionDto {

  private int id;

  private int portfolioId;

  private int parentComplexPositionId;

  private String ticker;

  private int size;
}
