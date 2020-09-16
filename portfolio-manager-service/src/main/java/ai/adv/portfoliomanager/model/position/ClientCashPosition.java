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
public class ClientCashPosition implements Position {

  private String ticker;
  private BigDecimal size;

  @Override
  public List<String> getTickers() {
    return Collections.singletonList(ticker);
  }

  @Override
  public Map<String, BigDecimal> getSharesWithNumbers() {
    return Collections.singletonMap(ticker, size);
  }

  @Override
  public List<Position> getPositions() {
    return Collections.singletonList(this);
  }

  @Override
  public BigDecimal getPositionValue(BigDecimal price) {
    return size;
  }

}
