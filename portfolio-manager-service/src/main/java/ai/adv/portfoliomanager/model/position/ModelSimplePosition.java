package ai.adv.portfoliomanager.model.position;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelSimplePosition {

  private String ticker;

  private BigDecimal share;
}
