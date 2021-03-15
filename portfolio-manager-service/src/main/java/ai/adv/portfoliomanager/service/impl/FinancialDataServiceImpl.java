package ai.adv.portfoliomanager.service.impl;

import ai.adv.financialdata.dto.StockPriceDto;
import ai.adv.financialdata.dto.StockPricesRequestDto;
import ai.adv.portfoliomanager.client.StockPriceClient;
import ai.adv.portfoliomanager.service.DataService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinancialDataServiceImpl implements DataService {

  private final StockPriceClient stockPriceClient;

  @Override
  public Map<String, BigDecimal> getPricesByTickers(List<String> tickers) {
    StockPricesRequestDto stockPricesRequestDto = new StockPricesRequestDto(tickers);
    return stockPriceClient.getStockPrices(stockPricesRequestDto).stream()
        .collect(Collectors.toMap(StockPriceDto::getTicker, this::getPriceForLot));
  }

  private BigDecimal getPriceForLot(StockPriceDto stockPriceDto) {
    BigDecimal lots = new BigDecimal(stockPriceDto.getLotSize());
    return stockPriceDto.getPrice().multiply(lots);
  }
}
