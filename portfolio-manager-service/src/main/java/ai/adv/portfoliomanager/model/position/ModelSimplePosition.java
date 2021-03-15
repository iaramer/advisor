package ai.adv.portfoliomanager.model.position;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ModelSimplePosition implements Position {

  private static final PositionType POSITION_TYPE = PositionType.MODEL_SIMPLE_POSITION;

  private String ticker;
  private BigDecimal proportion;

  public void addProportion(BigDecimal bigDecimal) {
    BigDecimal newProportion = proportion.add(bigDecimal);
    if (newProportion.compareTo(BigDecimal.ZERO) >= 0) {
      throw new UnsupportedOperationException(
          "A proportion of a position can't be higher than 100%");
    }
    proportion = newProportion;
  }

  @Override
  public PositionType getPositionType() {
    return POSITION_TYPE;
  }

  @Override
  public List<String> getTickers() {
    return Collections.singletonList(ticker);
  }

  @Override
  public Map<String, BigDecimal> getSharesWithNumbers() {
    throw new UnsupportedOperationException("Model portfolio can't have size");
  }

  @Override
  public BigDecimal getPositionValue(BigDecimal price) {
    throw new UnsupportedOperationException("Model portfolio can't have value");
  }

  @Override
  public List<Position> getPositions() {
    return Collections.singletonList(this);
  }
}
