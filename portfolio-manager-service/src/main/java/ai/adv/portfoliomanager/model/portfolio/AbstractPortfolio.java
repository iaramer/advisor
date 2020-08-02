package ai.adv.portfoliomanager.model.portfolio;

import ai.adv.portfoliomanager.model.position.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractPortfolio implements Portfolio {

  private String name;

  private String baseCurrency = "USD";

  private List<Position> positions;
}
