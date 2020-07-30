package ai.adv.portfoliomanager.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Portfolio {

  /**
   * Collection with positions
   */
  private List<Position> positions;
}
