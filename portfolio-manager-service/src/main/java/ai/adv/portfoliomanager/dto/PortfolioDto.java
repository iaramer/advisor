package ai.adv.portfoliomanager.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PortfolioDto {

  private int id;

  private String name;
}
