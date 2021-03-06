package balancing;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BalancerTest {

  private Balancer balancer;
  private String baseCurrency;

  @BeforeEach
  void setUp() {
    baseCurrency = "USD";
    balancer = new Balancer(baseCurrency);
  }

  @Test
  void formPortfolio_EmptyInputsZeroCash_EmptyMap() {
    Map<String, BigDecimal> modelPortfolio = Collections.emptyMap();
    Map<String, BigDecimal> prices = Collections.emptyMap();
    BigDecimal cashValue = BigDecimal.ZERO;

    Map<String, BigDecimal> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Assertions.assertEquals(Collections.emptyMap(), portfolio);
  }

  @Test
  void formPortfolio_EmptyModelPortfolioNonZeroPricesAndCash_MapWithUniqueCashPosition() {
    baseCurrency = "EUR";
    balancer = new Balancer(baseCurrency);

    Map<String, BigDecimal> modelPortfolio = Collections.emptyMap();
    Map<String, BigDecimal> prices = Collections.singletonMap("MSFT", BigDecimal.ONE);
    BigDecimal cashValue = BigDecimal.ONE;

    Map<String, BigDecimal> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Assertions.assertEquals(Collections.singletonMap(baseCurrency, cashValue), portfolio);
    BigDecimal portfolioValue = assessPortfolioValue(portfolio, prices, baseCurrency);
    Assertions.assertEquals(cashValue, portfolioValue);
  }

  @Test
  void formPortfolio_EmptyPricesNonEmptyModelPortfolioAndCash_IllegalArgumentException() {
    Map<String, BigDecimal> modelPortfolio = Collections.singletonMap("MSFT", BigDecimal.ONE);
    Map<String, BigDecimal> prices = Collections.emptyMap();
    BigDecimal cashValue = BigDecimal.ONE;

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> balancer.formPortfolio(modelPortfolio, prices, cashValue));
  }

  @Test
  void formPortfolio_ModelPortfolioWithZeroProportion_MapWithCash() {
    Map<String, BigDecimal> modelPortfolio = Collections.singletonMap("MSFT", BigDecimal.ZERO);
    Map<String, BigDecimal> prices = Collections.singletonMap("MSFT", BigDecimal.ONE);
    BigDecimal cashValue = BigDecimal.ONE;

    Map<String, BigDecimal> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Assertions.assertEquals(Collections.singletonMap(baseCurrency, BigDecimal.ONE), portfolio);
    BigDecimal portfolioValue = assessPortfolioValue(portfolio, prices, baseCurrency);
    Assertions.assertEquals(cashValue, portfolioValue);
  }

  @Test
  void formPortfolio_ModelPortfolioWithUniqueProportion_MapWithUniquePosition() {
    Map<String, BigDecimal> modelPortfolio = Collections.singletonMap("MSFT", BigDecimal.ONE);
    Map<String, BigDecimal> prices = Collections.singletonMap("MSFT", BigDecimal.ONE);
    BigDecimal cashValue = BigDecimal.ONE;

    Map<String, BigDecimal> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Assertions.assertEquals(Collections.singletonMap("MSFT", BigDecimal.ONE), portfolio);
    BigDecimal portfolioValue = assessPortfolioValue(portfolio, prices, baseCurrency);
    Assertions.assertEquals(cashValue, portfolioValue);
  }

  @Test
  void formPortfolio_ModelPortfolioWithTwoEqualProportions_MapWithTwoPositions() {
    Map<String, BigDecimal> modelPortfolio = new HashMap<>();
    modelPortfolio.put("AAPL", new BigDecimal("0.5"));
    modelPortfolio.put("MSFT", new BigDecimal("0.5"));
    Map<String, BigDecimal> prices = new HashMap<>();
    prices.put("AAPL", BigDecimal.TEN);
    prices.put("MSFT", BigDecimal.TEN);
    BigDecimal cashValue = new BigDecimal("20");

    Map<String, BigDecimal> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Map<String, BigDecimal> expectedPortfolio = new HashMap<>();
    expectedPortfolio.put("AAPL", BigDecimal.ONE);
    expectedPortfolio.put("MSFT", BigDecimal.ONE);
    Assertions.assertEquals(expectedPortfolio, portfolio);
    BigDecimal portfolioValue = assessPortfolioValue(portfolio, prices, baseCurrency);
    Assertions.assertEquals(cashValue, portfolioValue);
  }

  @Test
  void formPortfolio_ModelPortfolioWithTwoEqualShares_MapWithTwoPositions() {
    Map<String, BigDecimal> modelPortfolio = new HashMap<>();
    modelPortfolio.put("AAPL", BigDecimal.ONE);
    modelPortfolio.put("MSFT", BigDecimal.ONE);
    Map<String, BigDecimal> prices = new HashMap<>();
    prices.put("AAPL", BigDecimal.TEN);
    prices.put("MSFT", BigDecimal.TEN);
    BigDecimal cashValue = new BigDecimal("20");

    Map<String, BigDecimal> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Map<String, BigDecimal> expectedPortfolio = new HashMap<>();
    expectedPortfolio.put("AAPL", BigDecimal.ONE);
    expectedPortfolio.put("MSFT", BigDecimal.ONE);
    Assertions.assertEquals(expectedPortfolio, portfolio);
    BigDecimal portfolioValue = assessPortfolioValue(portfolio, prices, baseCurrency);
    Assertions.assertEquals(cashValue, portfolioValue);
  }

  @Test
  void formPortfolio_ModelPortfolioWithThreeEqualShares_MapWithThreePositions() {
    Map<String, BigDecimal> modelPortfolio = new HashMap<>();
    modelPortfolio.put("AAPL", BigDecimal.ONE);
    modelPortfolio.put("MSFT", BigDecimal.ONE);
    modelPortfolio.put("GOOGL", BigDecimal.ONE);
    Map<String, BigDecimal> prices = new HashMap<>();
    prices.put("AAPL", BigDecimal.TEN);
    prices.put("MSFT", BigDecimal.TEN);
    prices.put("GOOGL", BigDecimal.TEN);
    BigDecimal cashValue = new BigDecimal("31");

    Map<String, BigDecimal> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Map<String, BigDecimal> expectedPortfolio = new HashMap<>();
    expectedPortfolio.put("AAPL", BigDecimal.ONE);
    expectedPortfolio.put("MSFT", BigDecimal.ONE);
    expectedPortfolio.put("GOOGL", BigDecimal.ONE);
    expectedPortfolio.put(baseCurrency, BigDecimal.ONE);
    Assertions.assertEquals(expectedPortfolio, portfolio);
    BigDecimal portfolioValue = assessPortfolioValue(portfolio, prices, baseCurrency);
    Assertions.assertEquals(cashValue, portfolioValue);
  }

  @Test
  void formPortfolio_ModelPortfolioWithThreeUnsortedShares_MapWithTwoPositions() {
    Map<String, BigDecimal> modelPortfolio = new HashMap<>();
    modelPortfolio.put("AAPL", new BigDecimal("0.1"));
    modelPortfolio.put("MSFT", new BigDecimal("0.6"));
    modelPortfolio.put("GOOGL", new BigDecimal("0.3"));
    Map<String, BigDecimal> prices = new HashMap<>();
    prices.put("AAPL", new BigDecimal("1"));
    prices.put("MSFT", new BigDecimal("1000"));
    prices.put("GOOGL", new BigDecimal("10"));
    BigDecimal cashValue = new BigDecimal("100");

    Map<String, BigDecimal> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Map<String, BigDecimal> expectedPortfolio = new HashMap<>();
    expectedPortfolio.put("GOOGL", new BigDecimal("9"));
    expectedPortfolio.put("AAPL", new BigDecimal("10"));
    Assertions.assertEquals(expectedPortfolio, portfolio);
    BigDecimal portfolioValue = assessPortfolioValue(portfolio, prices, baseCurrency);
    Assertions.assertEquals(cashValue, portfolioValue);
  }

  @Test
  void formPortfolio_ModelPortfolioWithUnsortedShares_MapWithSortedPositions() {
    Map<String, BigDecimal> modelPortfolio = new HashMap<>();
    modelPortfolio.put("AAPL", new BigDecimal("0.1"));
    modelPortfolio.put("MSFT", new BigDecimal("0.6"));
    modelPortfolio.put("GOOGL", new BigDecimal("0.3"));
    Map<String, BigDecimal> prices = new HashMap<>();
    prices.put("AAPL", new BigDecimal("1"));
    prices.put("MSFT", new BigDecimal("1"));
    prices.put("GOOGL", new BigDecimal("1"));
    BigDecimal cashValue = new BigDecimal("100");

    Map<String, BigDecimal> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Map<String, BigDecimal> expectedPortfolio = new HashMap<>();
    expectedPortfolio.put("MSFT", new BigDecimal("60"));
    expectedPortfolio.put("GOOGL", new BigDecimal("30"));
    expectedPortfolio.put("AAPL", new BigDecimal("10"));
    Assertions.assertEquals(expectedPortfolio, portfolio);
    BigDecimal portfolioValue = assessPortfolioValue(portfolio, prices, baseCurrency);
    Assertions.assertEquals(cashValue, portfolioValue);
  }

  @Test
  void formPortfolio_ModelPortfolioWithTwoShares_MapWithUniquePositionAddedBySecondIteration() {
    Map<String, BigDecimal> modelPortfolio = new HashMap<>();
    modelPortfolio.put("AAPL", new BigDecimal("0.6"));
    modelPortfolio.put("MSFT", new BigDecimal("0.4"));
    Map<String, BigDecimal> prices = new HashMap<>();
    prices.put("AAPL", new BigDecimal("100"));
    prices.put("MSFT", new BigDecimal("100"));
    BigDecimal cashValue = new BigDecimal("100");

    Map<String, BigDecimal> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Map<String, BigDecimal> expectedPortfolio = new HashMap<>();
    expectedPortfolio.put("AAPL", new BigDecimal("1"));
    Assertions.assertEquals(expectedPortfolio, portfolio);
    BigDecimal portfolioValue = assessPortfolioValue(portfolio, prices, baseCurrency);
    Assertions.assertEquals(cashValue, portfolioValue);
  }

  @Test
  void formPortfolio_ModelPortfolioWithUniqueCashShare_MapWithUniqueCashPosition() {
    baseCurrency = "EUR";
    balancer = new Balancer(baseCurrency);

    Map<String, BigDecimal> modelPortfolio = new HashMap<>();
    modelPortfolio.put(baseCurrency, new BigDecimal("1"));
    Map<String, BigDecimal> prices = Collections.emptyMap();
    BigDecimal cashValue = new BigDecimal("100");

    Map<String, BigDecimal> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Map<String, BigDecimal> expectedPortfolio = new HashMap<>();
    expectedPortfolio.put(baseCurrency, new BigDecimal("100"));
    Assertions.assertEquals(expectedPortfolio, portfolio);
    BigDecimal portfolioValue = assessPortfolioValue(portfolio, prices, baseCurrency);
    Assertions.assertEquals(cashValue, portfolioValue);
  }

  @Test
  void formPortfolio_PricesIncludeBaseCurrency_MapWithPositionIgnoringBaseCurrencyPriceProvided() {
    balancer = new Balancer(baseCurrency);

    Map<String, BigDecimal> modelPortfolio = new HashMap<>();
    modelPortfolio.put(baseCurrency, new BigDecimal("0.6"));
    modelPortfolio.put("MSFT", new BigDecimal("0.4"));
    Map<String, BigDecimal> prices = new HashMap<>();
    prices.put(baseCurrency, new BigDecimal("60"));
    prices.put("MSFT", new BigDecimal("1"));
    BigDecimal cashValue = new BigDecimal("100");

    Map<String, BigDecimal> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Map<String, BigDecimal> expectedPortfolio = new HashMap<>();
    expectedPortfolio.put(baseCurrency, new BigDecimal("60"));
    expectedPortfolio.put("MSFT", new BigDecimal("40"));
    Assertions.assertEquals(expectedPortfolio, portfolio);
    BigDecimal portfolioValue = assessPortfolioValue(portfolio, prices, baseCurrency);
    Assertions.assertEquals(cashValue, portfolioValue);
  }

  @Test
  void formPortfolio_PricesDoNotIncludeTicker_IllegalArgumentException() {
    Map<String, BigDecimal> modelPortfolio = new HashMap<>();
    modelPortfolio.put("AAPL", new BigDecimal("0.6"));
    modelPortfolio.put("MSFT", new BigDecimal("0.4"));
    Map<String, BigDecimal> prices = new HashMap<>();
    prices.put("AAPL", new BigDecimal("1"));
    prices.put("GOOGL", new BigDecimal("1"));
    BigDecimal cashValue = new BigDecimal("100");

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> balancer.formPortfolio(modelPortfolio, prices, cashValue));
  }

  @Test
  void formPortfolio_ModelPortfolioWithUniqueSharePricesIncludeExtraTicker_MapWithUniquePosition() {
    Map<String, BigDecimal> modelPortfolio = new HashMap<>();
    modelPortfolio.put("AAPL", BigDecimal.ONE);
    Map<String, BigDecimal> prices = new HashMap<>();
    prices.put("AAPL", new BigDecimal("1"));
    prices.put("MSFT", new BigDecimal("1"));
    BigDecimal cashValue = new BigDecimal("100");

    Map<String, BigDecimal> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Map<String, BigDecimal> expectedPortfolio = new HashMap<>();
    expectedPortfolio.put("AAPL", new BigDecimal("100"));
    Assertions.assertEquals(expectedPortfolio, portfolio);
    BigDecimal portfolioValue = assessPortfolioValue(portfolio, prices, baseCurrency);
    Assertions.assertEquals(cashValue, portfolioValue);
  }

  @Test
  void formPortfolio_ModelPortfolioWithNegativeCash_EmptyMap() {
    Map<String, BigDecimal> modelPortfolio = Collections.singletonMap("AAPL", BigDecimal.ONE);
    Map<String, BigDecimal> prices = Collections.singletonMap("AAPL", BigDecimal.ONE);
    BigDecimal cashValue = new BigDecimal("-100");

    Map<String, BigDecimal> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Map<String, BigDecimal> expectedPortfolio = Collections.emptyMap();
    Assertions.assertEquals(expectedPortfolio, portfolio);
  }

  @Test
  void formPortfolio_ModelPortfolioWithSecondIterationNeeded_MapWithPositionsAndCash() {
    Map<String, BigDecimal> modelPortfolio = new HashMap<>();
    modelPortfolio.put("AAPL", new BigDecimal("0.1"));
    modelPortfolio.put("MSFT", new BigDecimal("0.3"));
    modelPortfolio.put("GOOGL", new BigDecimal("0.6"));
    Map<String, BigDecimal> prices = new HashMap<>();
    prices.put("AAPL", new BigDecimal("0.99"));
    prices.put("MSFT", new BigDecimal("3"));
    prices.put("GOOGL", new BigDecimal("4"));
    BigDecimal cashValue = new BigDecimal("10");

    Map<String, BigDecimal> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Map<String, BigDecimal> expectedPortfolio = new HashMap<>();
    expectedPortfolio.put("AAPL", new BigDecimal("3"));
    expectedPortfolio.put("MSFT", new BigDecimal("1"));
    expectedPortfolio.put("GOOGL", new BigDecimal("1"));
    expectedPortfolio.put("USD", new BigDecimal("0.03"));
    Assertions.assertEquals(expectedPortfolio, portfolio);
    BigDecimal portfolioValue = assessPortfolioValue(portfolio, prices, baseCurrency);
    Assertions.assertEquals(cashValue, portfolioValue);
  }

  private BigDecimal assessPortfolioValue(Map<String, BigDecimal> positions,
      Map<String, BigDecimal> prices, String baseCurrency) {
    prices = new HashMap<>(prices);
    prices.put(baseCurrency, BigDecimal.ONE);
    BigDecimal portfolioValue = BigDecimal.ZERO;

    for (Entry<String, BigDecimal> position : positions.entrySet()) {
      BigDecimal securityPrice = prices.get(position.getKey());
      BigDecimal positionValue = position.getValue().multiply(securityPrice);
      portfolioValue = portfolioValue.add(positionValue);
    }

    return new BigDecimal(portfolioValue.stripTrailingZeros().toPlainString());
  }
}
