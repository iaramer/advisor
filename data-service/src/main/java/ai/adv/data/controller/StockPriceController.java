package ai.adv.data.controller;

import ai.adv.data.dto.StockPriceDto;
import ai.adv.data.dto.StockPricesRequestDto;
import ai.adv.data.service.StockPriceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/stock-prices")
@RequiredArgsConstructor
public class StockPriceController {

  private final StockPriceService stockPriceService;

  @GetMapping(value = "/get/{ticker}")
  public StockPriceDto getStockPrice(@PathVariable String ticker) {
    return stockPriceService.getStockPrice(ticker);
  }

  @PostMapping(value = "/get")
  public List<StockPriceDto> getStockPrices(@RequestBody StockPricesRequestDto stockPricesRequestDto) {
    return stockPriceService.getStockPrices(stockPricesRequestDto);
  }
}
