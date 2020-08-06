package ai.adv.portfoliomanager.model.position;

import java.util.Collections;
import java.util.List;
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
}
