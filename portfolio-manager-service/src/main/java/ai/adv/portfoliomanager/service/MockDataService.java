package ai.adv.portfoliomanager.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Deprecated
public class MockDataService {

  private static final Map<String, BigDecimal> pricesOfPortfolioShares = new HashMap<>();

  static {
    pricesOfPortfolioShares.put("RUB", new BigDecimal("1"));
    pricesOfPortfolioShares.put("USD", new BigDecimal("75.16"));
    pricesOfPortfolioShares.put("FXIT", new BigDecimal("5456.12"));
    pricesOfPortfolioShares.put("SBSP", new BigDecimal("1274.78"));
    pricesOfPortfolioShares.put("SBCB", new BigDecimal("1077.23"));
    pricesOfPortfolioShares.put("FXGD", new BigDecimal("778.56"));
  }

  public Map<String, BigDecimal> getPricesByTickers(List<String> tickers, String currency) {
    return pricesOfPortfolioShares.entrySet().stream()
        .filter(entry -> tickers.contains(entry.getKey()))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }
}
