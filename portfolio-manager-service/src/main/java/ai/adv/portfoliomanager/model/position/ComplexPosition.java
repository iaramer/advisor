package ai.adv.portfoliomanager.model.position;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

/**
 * Describes combined position, such as country allocation or sector allocation. May contain both complex and single
 * positions.
 */
@Getter
@Setter
public class ComplexPosition implements Position {

  private String description;

  private List<Position> positions;

  @Override
  public List<String> getTickers() {
    return positions.stream()
        .map(Position::getTickers)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }
}
