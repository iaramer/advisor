package ai.adv.financialdata.service.schedule;

import ai.adv.financialdata.api.DataAPI;
import ai.adv.financialdata.service.StockPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataMiner {

  private final DataAPI dataAPI;
  private final StockPriceService stockPriceService;

  @Scheduled(cron = "${cron.refresh.stock-prices}")
  public void refreshStockPrices() {
    stockPriceService.saveStockPrices(dataAPI.getStockPrices());
  }
}
