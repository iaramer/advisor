package ai.adv.portfoliomanager.service;

import ai.adv.portfoliomanager.model.Portfolio;
import java.math.BigDecimal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortfolioValuationService {

  private static final String NO_PRICES_PROVIDED = "No prices provided";

  public BigDecimal getPortfolioValue(Portfolio portfolio, Map<String, BigDecimal> sharesPrices) {
    if (MapUtils.isEmpty(sharesPrices)) {
      log.error(NO_PRICES_PROVIDED);
      throw new IllegalArgumentException(NO_PRICES_PROVIDED);
    }
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
