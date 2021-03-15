package ai.adv.portfoliomanager.client;

import ai.adv.financialdata.dto.StockPriceDto;
import ai.adv.financialdata.dto.StockPricesRequestDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("financial-data-service")
public interface StockPriceClient {

  @GetMapping("/stock-prices/{ticker}")
  StockPriceDto getStockPrice(@PathVariable String ticker);

  @PostMapping(value = "/stock-prices/list", consumes = "application/json")
  List<StockPriceDto> getStockPrices(@RequestBody StockPricesRequestDto stockPricesRequestDto);
}
