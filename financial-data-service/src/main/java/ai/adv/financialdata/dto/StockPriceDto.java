package ai.adv.financialdata.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockPriceDto {

  private String ticker;
  private String exchange;
  private BigDecimal price;
  private int decimals;
  private int lotSize;
}
