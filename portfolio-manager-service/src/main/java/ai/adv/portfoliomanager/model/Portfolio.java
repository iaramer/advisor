package ai.adv.portfoliomanager.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Portfolio {

  private String name;

  private boolean isModel;
  private String baseCurrency;
  private List<Position> positions = new ArrayList<>();

  public Portfolio(String name, String baseCurrency) {
    this.name = name;
    this.baseCurrency = baseCurrency;
  }

  public List<String> getTickers() {
    return positions.stream()
        .map(Position::getTicker)
        .collect(Collectors.toList());
  }
}
