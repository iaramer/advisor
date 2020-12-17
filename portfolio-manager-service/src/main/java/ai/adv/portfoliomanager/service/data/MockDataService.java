package ai.adv.portfoliomanager.service.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Deprecated
@RequiredArgsConstructor
public class MockDataService implements DataService {

  private static final Map<String, BigDecimal> pricesOfPortfolioShares = new HashMap<>();

  static {
    pricesOfPortfolioShares.put("SBSP", new BigDecimal("1439.2"));
    pricesOfPortfolioShares.put("FXIT", new BigDecimal("9349"));
    pricesOfPortfolioShares.put("FXCN", new BigDecimal("3943.5"));
    pricesOfPortfolioShares.put("VTBE", new BigDecimal("902"));
    pricesOfPortfolioShares.put("FXRU", new BigDecimal("935.4"));
    pricesOfPortfolioShares.put("VTBH", new BigDecimal("782.4"));
    pricesOfPortfolioShares.put("FXGD", new BigDecimal("926.6"));
    pricesOfPortfolioShares.put("FXTB", new BigDecimal("742.0"));

    pricesOfPortfolioShares.put("USD", new BigDecimal("75.16"));
    pricesOfPortfolioShares.put("SBCB", new BigDecimal("1077.23"));
    pricesOfPortfolioShares.put("FXUS", new BigDecimal("4879.17"));
  }

  @Override
  public Map<String, BigDecimal> getPricesByTickers(List<String> tickers) {
    return pricesOfPortfolioShares.entrySet().stream()
        .filter(entry -> tickers.contains(entry.getKey()))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }
}
