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

  private String ticker;

  private BigDecimal proportion;

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
