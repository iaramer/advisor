package balancing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Balancer {

  private final String currencyTicker;

  /**
   * Формирует портфель. Возвращает мапу "тикер"-"колличество акций". Не зависит от валюты актива.
   */
  public Map<String, Integer> formPortfolio(Map<String, BigDecimal> modelPortfolio,
      Map<String, BigDecimal> prices, BigDecimal cashValue) {

    if (modelPortfolio.isEmpty() || cashValue.equals(BigDecimal.ZERO)) {
      return Collections.emptyMap();
    }
    if (prices.isEmpty()) {
      throw new IllegalArgumentException("No prices provided");
    }

    //todo: check all prices exist

    Map<String, Integer> portfolio = new HashMap<>();

    BigDecimal valueRemainder = cashValue;
    List<Entry<String, BigDecimal>> modelPortfolioEntries = modelPortfolio.entrySet().stream()
        .sorted(Entry.comparingByValue()) //todo: check the order is really DESC. We need the highest proportions to go first
        .collect(Collectors.toList());

    for (Entry<String, BigDecimal> entry : modelPortfolioEntries) {
      if (entry.getValue().compareTo(BigDecimal.ZERO) <= 0) {
        continue;
      }
      BigDecimal exactPositionValue = entry.getValue().multiply(cashValue);
      BigDecimal stockPrice = prices.get(entry.getKey());
      BigDecimal numberOfSecurities = exactPositionValue.divide(stockPrice, RoundingMode.FLOOR);

      BigDecimal newPositionValue = stockPrice.multiply(numberOfSecurities);
      valueRemainder = valueRemainder.subtract(newPositionValue);
      portfolio.put(entry.getKey(), numberOfSecurities.intValueExact());
    }

    return portfolio;
  }
}