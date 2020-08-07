package ai.adv.portfoliomanager.service;

import ai.adv.portfoliomanager.model.Portfolio;
import java.math.BigDecimal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioValuationService {

  public BigDecimal getPortfolioValue(Portfolio portfolio, Map<String, BigDecimal> sharesPrices) {
    Map<String, Integer> sharesWithNumbers = portfolio.getSharesWithNumbers();
    return sharesWithNumbers.entrySet().stream()
        .map(entry -> {
          BigDecimal price = sharesPrices.get(entry.getKey());
          BigDecimal number = new BigDecimal(entry.getValue());
          return price.multiply(number);
        })
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.ZERO);
  }
}
