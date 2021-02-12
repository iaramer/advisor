package ai.adv.financialdata.service.schedule;

import ai.adv.financialdata.api.DataAPI;
import ai.adv.financialdata.dto.StockPriceDto;
import ai.adv.financialdata.service.StockPriceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataMiner {

  private final DataAPI dataAPI;
  private final StockPriceService stockPriceService;

  @Scheduled(cron = "${cron.refresh.stock-prices}")
  public void refreshStockPrices() {
    log.info("Getting stock prices");

    List<StockPriceDto> stockPriceDtos = dataAPI.getStockPrices();
    stockPriceService.saveStockPrices(stockPriceDtos);

    log.info("Prices for {} stocks were updated", stockPriceDtos.size());
  }
}
