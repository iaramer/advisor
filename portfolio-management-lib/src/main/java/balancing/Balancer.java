package balancing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class Balancer {

  private final String baseCurrencyTicker;

  /**
   * Forms an investments portfolio. Returns a Map "Ticker"-"Number of shares". Prices should be
   * given in Base Currency.
   */
  public Map<String, BigDecimal> formPortfolio(Map<String, BigDecimal> modelPortfolio,
      Map<String, BigDecimal> prices, BigDecimal cashValue) {
    if (cashValue.compareTo(BigDecimal.ZERO) <= 0) {
      return Collections.emptyMap();
    }
    if (modelPortfolio.isEmpty() || hasOnlyCashShare(modelPortfolio)) {
      return Collections.singletonMap(baseCurrencyTicker, cashValue);
    }
    if (prices.isEmpty()) {
      throw new IllegalArgumentException("No prices provided");
    }

    modelPortfolio = new HashMap<>(modelPortfolio);
    prices = new HashMap<>(prices);
    checkNecessaryPricesProvided(prices, modelPortfolio);

    Map<String, BigDecimal> portfolio = new HashMap<>();
    BigDecimal valueRemainder = cashValue;

    List<Entry<String, BigDecimal>> modelPortfolioEntries = sort(modelPortfolio);
    List<Entry<String, BigDecimal>> normalizedModelPortfolioEntries = normalize(
        modelPortfolioEntries);

    for (Entry<String, BigDecimal> entry : normalizedModelPortfolioEntries) {
      if (entry.getValue().compareTo(BigDecimal.ZERO) <= 0) {
        continue;
      }
      BigDecimal exactPositionValue = cashValue.multiply(entry.getValue());
      BigDecimal securityPrice = prices.get(entry.getKey());
      BigDecimal initialSecuritiesNumber = exactPositionValue
          .divide(securityPrice, 0, RoundingMode.FLOOR);
      if (initialSecuritiesNumber.compareTo(BigDecimal.ZERO) <= 0) {
        continue;
      }

      BigDecimal newPositionValue = securityPrice.multiply(initialSecuritiesNumber);
      valueRemainder = valueRemainder.subtract(newPositionValue);
      portfolio.put(entry.getKey(), initialSecuritiesNumber.setScale(0, RoundingMode.DOWN));
    }

    for (Entry<String, BigDecimal> entry : normalizedModelPortfolioEntries) {
      String ticker = entry.getKey();

      BigDecimal securityPrice = prices.get(ticker);
      BigDecimal additionalSecuritiesNumber = valueRemainder
          .divide(securityPrice, 0, RoundingMode.FLOOR);
      if (additionalSecuritiesNumber.compareTo(BigDecimal.ZERO) <= 0) {
        continue;
      }

      BigDecimal initialSecuritiesNumber = Optional.ofNullable(portfolio.get(ticker))
          .orElse(BigDecimal.ZERO);
      BigDecimal finalSecuritiesNumber = additionalSecuritiesNumber.add(initialSecuritiesNumber);
      valueRemainder = valueRemainder.subtract(finalSecuritiesNumber.multiply(securityPrice));
      portfolio.put(ticker, finalSecuritiesNumber.setScale(0, RoundingMode.DOWN));
    }

    if (valueRemainder.compareTo(BigDecimal.ZERO) > 0) {
      portfolio.put(baseCurrencyTicker, valueRemainder);
    }

    return portfolio;
  }

  void checkNecessaryPricesProvided(Map<String, BigDecimal> prices,
      Map<String, BigDecimal> modelPortfolio) {
    prices.put(baseCurrencyTicker, BigDecimal.ONE);
    for (Entry<String, BigDecimal> entry : modelPortfolio.entrySet()) {
      Optional.ofNullable(prices.get(entry.getKey()))
          .orElseThrow(IllegalArgumentException::new);
    }
  }

  private boolean hasOnlyCashShare(Map<String, BigDecimal> modelPortfolio) {
    return modelPortfolio.size() == 1
        && modelPortfolio.get(baseCurrencyTicker) != null;
  }

  private List<Entry<String, BigDecimal>> sort(Map<String, BigDecimal> modelPortfolio) {
    return modelPortfolio.entrySet().stream()
        .filter(this::isNotEmptyOrZero)
        .sorted((f1, f2) -> -f1.getValue().compareTo(f2.getValue()))
        .collect(Collectors.toList());
  }

  private List<Entry<String, BigDecimal>> normalize(
      List<Entry<String, BigDecimal>> modelPortfolioEntries) {
    if (modelPortfolioEntries.isEmpty()) {
      return Collections.emptyList();
    }

    BigDecimal summaryShares = BigDecimal.ZERO;
    List<Entry<String, BigDecimal>> normalizedEntries = new ArrayList<>();
    for (Entry<String, BigDecimal> entry : modelPortfolioEntries) {
      summaryShares = summaryShares.add(entry.getValue());
    }
    for (Entry<String, BigDecimal> entry : modelPortfolioEntries) {
      BigDecimal percentage = entry.getValue().divide(summaryShares, 3, RoundingMode.FLOOR);
      normalizedEntries.add(new AbstractMap.SimpleEntry<>(entry.getKey(), percentage));
    }
    return normalizedEntries;
  }

  private boolean isNotEmptyOrZero(Entry<String, BigDecimal> entry) {
    String ticker = entry.getKey();
    BigDecimal number = entry.getValue();
    return StringUtils.isNoneBlank(ticker)
        && number != null
        && number.compareTo(BigDecimal.ZERO) > 0;
  }
}