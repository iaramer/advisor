package ai.adv.portfoliomanager.model;

import ai.adv.portfoliomanager.model.position.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Portfolio {

  private String name;

  private boolean isModel;

  private String baseCurrency = "USD";

  private List<Position> positions;
}
