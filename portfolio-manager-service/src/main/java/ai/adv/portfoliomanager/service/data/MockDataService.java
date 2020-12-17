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
    pricesOfPortfolioShares.put("SBSP", new BigDecimal("1274.78"));
    pricesOfPortfolioShares.put("FXIT", new BigDecimal("5456.12"));
    pricesOfPortfolioShares.put("FXCN", new BigDecimal("3224.12"));
    pricesOfPortfolioShares.put("VTBE", new BigDecimal("1033.12"));
    pricesOfPortfolioShares.put("FXRU", new BigDecimal("849.12"));
    pricesOfPortfolioShares.put("VTBH", new BigDecimal("1546.12"));
    pricesOfPortfolioShares.put("FXGD", new BigDecimal("877.56"));
    pricesOfPortfolioShares.put("FXTB", new BigDecimal("1078.98"));

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
