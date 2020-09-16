package ai.adv.portfoliomanager.model.position;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Describes simple allocation, such as share or ETF. Could be a part of ComplexPosition.
 *
 * @See ComplexPosition
 */
@Getter
@Setter
@Builder
public class ClientSimplePosition implements Position {

  private String ticker;
  private int size;

  @Override
  public List<String> getTickers() {
    return Collections.singletonList(ticker);
  }

  @Override
  public Map<String, BigDecimal> getSharesWithNumbers() {
    return Collections.singletonMap(ticker, BigDecimal.valueOf(size));
  }

  @Override
  public List<Position> getPositions() {
    return Collections.singletonList(this);
  }

  @Override
  public BigDecimal getPositionValue(BigDecimal price) {
    return BigDecimal.valueOf(size).multiply(price);
  }
}
