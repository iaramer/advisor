package ai.adv.portfoliomanager.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PortfolioDto {

  private UUID uuid;

  private boolean isModel;

  private String baseCurrency;

  private String name;
}
