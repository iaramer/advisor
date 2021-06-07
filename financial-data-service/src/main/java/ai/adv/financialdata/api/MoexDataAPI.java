package ai.adv.financialdata.api;

import ai.adv.financialdata.dto.StockPriceDto;
import ai.adv.financialdata.utils.xml.MoexXmlProcessor;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoexDataAPI implements DataAPI {

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
  private final MoexXmlProcessor moexXmlProcessor;

  @Override
  public List<StockPriceDto> getStockPrices() {
    List<StockPriceDto> stockPriceDtos = new ArrayList<>();
    stockPriceDtos.addAll(getPrices(stockRub));
    stockPriceDtos.addAll(getPrices(etfRub));
    return stockPriceDtos;
  }

  public List<StockPriceDto> getPrices(String instrument) {
    String url = urlBoards + instrument + urlParams;

    String responseBody = httpClientService.get(url);

    return moexXmlProcessor.process(responseBody);
  }
}
