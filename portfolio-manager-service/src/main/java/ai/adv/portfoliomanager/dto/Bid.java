package ai.adv.portfoliomanager.dto;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Bid {

  /**
   * Ticker of share
   */
  private String ticker;

  /**
   * Number of shares. Positive - buy, negative - sell
   */
  private int number;

  /**
   * price of share. Null if market price
   */
  @Nullable
  private BigDecimal price;
}
