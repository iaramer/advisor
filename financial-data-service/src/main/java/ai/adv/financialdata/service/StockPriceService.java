package ai.adv.financialdata.service;

import ai.adv.financialdata.dto.StockPriceDto;
import ai.adv.financialdata.dto.StockPricesRequestDto;
import ai.adv.financialdata.repository.StockPriceRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockPriceService {

  private final StockPriceRepository stockPriceRepository;

  public StockPriceDto getStockPrice(String ticker) {
    return stockPriceRepository.getStockPricesByTickers(Collections.singletonList(ticker))
        .stream()
        .findAny()
        .orElse(null);
  }

  public List<StockPriceDto> getStockPrices(StockPricesRequestDto stockPricesRequestDto) {
    return stockPriceRepository.getStockPricesByTickers(stockPricesRequestDto.getTickers());
  }

  public List<StockPriceDto> saveStockPrices(List<StockPriceDto> stockPriceDtos) {
    return stockPriceRepository.saveStockPrices(stockPriceDtos);
  }
}
