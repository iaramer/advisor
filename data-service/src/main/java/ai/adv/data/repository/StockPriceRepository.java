package ai.adv.data.repository;

import ai.adv.data.dto.StockPriceDto;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class StockPriceRepository {

  public List<StockPriceDto> getStockPricesByTickers(List<String> tickers) {
    return Collections.emptyList();
  }
}
