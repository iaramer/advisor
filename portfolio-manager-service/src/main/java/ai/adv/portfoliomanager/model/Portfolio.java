package ai.adv.portfoliomanager.model;

import ai.adv.portfoliomanager.model.position.Position;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Portfolio {

  public static final String DEFAULT_BASE_CURRENCY = "USD";

  private String name;

  private boolean isModel;

  @Builder.Default
  private String baseCurrency = DEFAULT_BASE_CURRENCY;

  private List<Position> positions;
}
