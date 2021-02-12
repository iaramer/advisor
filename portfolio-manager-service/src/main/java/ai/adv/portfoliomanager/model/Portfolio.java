package ai.adv.portfoliomanager.model;

import ai.adv.portfoliomanager.model.position.Position;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.AccessLevel;
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
  @Getter(AccessLevel.NONE)
  @Builder.Default
  private List<Position> positions = new ArrayList<>();

  public List<Position> getPositions() {
    return positions.stream()
        .map(Position::getPositions)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  public List<String> getTickers() {
    return positions.stream()
        .map(Position::getTickers)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  //todo: obtain different scales
  public Map<String, BigDecimal> getSharesWithNumbers() {
    return positions.stream()
        .map(Position::getSharesWithNumbers)
        .flatMap(position -> position.entrySet().stream())
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }
}
