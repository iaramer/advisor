package ai.adv.portfoliomanager.service;

import ai.adv.portfoliomanager.model.Portfolio;
import java.math.BigDecimal;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.annotation.Nonnull;
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
    if (portfolio.isModel()) {
      throw new UnsupportedOperationException("Unable to determine model portfolio value");
    }
    if (MapUtils.isEmpty(sharesPrices)) {
      log.error(NO_PRICES_PROVIDED);
      throw new IllegalArgumentException(NO_PRICES_PROVIDED);
    }
    Map<String, BigDecimal> sharesWithNumbers = portfolio.getSharesWithNumbers();
    return sharesWithNumbers.entrySet().stream()
        .map(entry -> getShareValue(sharesPrices, entry))
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.ZERO);
  }

  @Nonnull
  private BigDecimal getShareValue(Map<String, BigDecimal> sharesPrices, Map.Entry<String, BigDecimal> entry) {
    BigDecimal price = Optional.ofNullable(sharesPrices.get(entry.getKey()))
        .orElseThrow(() -> new NoSuchElementException(String.format("No price for %s provided", entry.getKey())));
    BigDecimal number = entry.getValue();
    return price.multiply(number);
  }
}
