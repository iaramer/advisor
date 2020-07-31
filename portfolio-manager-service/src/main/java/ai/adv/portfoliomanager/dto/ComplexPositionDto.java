package ai.adv.portfoliomanager.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ComplexPositionDto {

  private int id;

  private int portfolioId;

  private int parentComplexPositionId;

  private String description;
}
