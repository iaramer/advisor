package ai.adv.data.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockPriceDto {

  private String ticker;
  private BigDecimal price;
}
