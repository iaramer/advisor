package ai.adv.portfoliomanager.model.position;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Describes combined position, such as country allocation or sector allocation. May contain both complex and single
 * positions.
 */
@Getter
@Setter
public class ComplexPosition implements Position {

  private static final PositionType POSITION_TYPE = PositionType.COMPLEX_POSITION;

  private String name;
  @Getter(AccessLevel.NONE)
  private List<Position> positions = new ArrayList<>();

  @Override
  public PositionType getPositionType() {
    return POSITION_TYPE;
  }

  @Override
  public List<String> getTickers() {
    return positions.stream()
        .map(Position::getTickers)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  @Override
  public Map<String, BigDecimal> getSharesWithNumbers() {
    return positions.stream()
        .map(Position::getSharesWithNumbers)
        .flatMap(position -> position.entrySet().stream())
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }

  public void addPosition(Position position) {
    positions.add(position);
  }

  @Override
  public List<Position> getPositions() {
    return positions.stream()
        .map(Position::getPositions)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  @Override
  public BigDecimal getPositionValue(BigDecimal price) {
    return positions.stream()
        .map(position -> position.getPositionValue(price))
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.ZERO);
  }
}
