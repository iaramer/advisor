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
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class Balancer {

  private final String currencyTicker;

  /**
   * Формирует портфель. Возвращает мапу "тикер"-"колличество акций". Не зависит от валюты актива.
   */
  public Map<String, BigDecimal> formPortfolio(Map<String, BigDecimal> modelPortfolio,
      Map<String, BigDecimal> prices, BigDecimal cashValue) {

    if (modelPortfolio.isEmpty() || cashValue.equals(BigDecimal.ZERO)) {
      return Collections.emptyMap();
    }
    if (prices.isEmpty()) {
      throw new IllegalArgumentException("No prices provided");
    }

    //todo: check all prices exist

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

      BigDecimal newPositionValue = securityPrice.multiply(initialSecuritiesNumber);
      valueRemainder = valueRemainder.subtract(newPositionValue);
      portfolio.put(entry.getKey(), initialSecuritiesNumber.setScale(0, RoundingMode.DOWN));
    }

    for (Entry<String, BigDecimal> entry : normalizedModelPortfolioEntries) {
      BigDecimal securityPrice = prices.get(entry.getKey());
      BigDecimal additionalSecuritiesNumber = valueRemainder
          .divide(securityPrice, 0, RoundingMode.FLOOR);
      if (additionalSecuritiesNumber.compareTo(BigDecimal.ZERO) <= 0) {
        continue;
      }

      BigDecimal initialSecuritiesNumber = portfolio.get(entry.getKey()); //todo: what if there's no such key in portfolio from the first iteration
      BigDecimal finalSecuritiesNumber = additionalSecuritiesNumber.add(initialSecuritiesNumber);
      valueRemainder = valueRemainder.subtract(finalSecuritiesNumber.multiply(securityPrice));
      portfolio.put(entry.getKey(), finalSecuritiesNumber.setScale(0, RoundingMode.DOWN));
    }

    if (valueRemainder.compareTo(BigDecimal.ZERO) > 0) {
      portfolio.put(currencyTicker, valueRemainder);
    }

    return portfolio;
  }

  private List<Entry<String, BigDecimal>> sort(Map<String, BigDecimal> modelPortfolio) {
    return modelPortfolio.entrySet().stream()
        .filter(this::isNotEmptyOrZero)
        .sorted(Entry
            .comparingByValue()) //todo: check the order is really DESC. We need the highest proportions to go first
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