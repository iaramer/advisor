package ai.adv.portfoliomanager.model.position;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ModelSimplePosition implements Position {

  private String ticker;

  private BigDecimal proportion;
}
