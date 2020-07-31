package ai.adv.portfoliomanager.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Portfolio {

  private String name;

  private List<Position> positions;
}
