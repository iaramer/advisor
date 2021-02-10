package ai.adv.data.api;

import ai.adv.data.dto.StockPriceDto;
import java.util.List;

public interface DataAPI {

  List<StockPriceDto> getStockPrices();
}
