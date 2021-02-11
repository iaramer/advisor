package ai.adv.financialdata.repository;

import ai.adv.financialdata.dto.StockPriceDto;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class StockPriceRepository {

  public List<StockPriceDto> getStockPricesByTickers(List<String> tickers) {
    return Collections.emptyList();
  }

  public List<StockPriceDto> saveStockPrices(List<StockPriceDto> stockPriceDtos) {
    return Collections.emptyList();
  }
}
