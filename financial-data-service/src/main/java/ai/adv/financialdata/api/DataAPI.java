package ai.adv.financialdata.api;

import ai.adv.financialdata.dto.StockPriceDto;
import java.util.List;

public interface DataAPI {

  List<StockPriceDto> getStockPrices();
}
