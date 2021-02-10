package ai.adv.data.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockPriceDto {

  private String ticker;
  private BigDecimal price;
  private int decimals;
  private int lotSize;
}
