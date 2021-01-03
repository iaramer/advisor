package ai.adv.data.api;

import ai.adv.data.dto.StockPriceDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MOEXDataAPI implements DataAPI {

  @Value("${api.moex.url.boards}")
  private String urlBoards;
  @Value("${api.moex.url.params}")
  private String urlParams;

  @Value("${api.moex.stock-rub}")
  private String stockRub;
  @Value("${api.moex.etf-usd}")
  private String etfUsd;
  @Value("${api.moex.etf-rub}")
  private String etfRub;

  private final HttpClientService httpClientService;

  @Override
  @Scheduled(cron = "${cron.refresh.stock-prices}")
  public List<StockPriceDto> getStockPrices() {
    List<StockPriceDto> stockPrices = new ArrayList<>();
    String url = urlBoards + stockRub + urlParams;

    String responseBody = httpClientService.get(url);

    return stockPrices;
  }
}
