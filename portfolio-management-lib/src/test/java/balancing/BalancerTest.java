package balancing;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BalancerTest {

  private Balancer balancer;

  @BeforeEach
  void setUp() {
    balancer = new Balancer("USD");
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
  void formPortfolio_EmptyModelPortfolioNonZeroPricesAndCash_EmptyMap() {
    Map<String, BigDecimal> modelPortfolio = Collections.emptyMap();
    Map<String, BigDecimal> prices = Collections.singletonMap("MSFT", BigDecimal.ONE);
    BigDecimal cashValue = BigDecimal.ONE;

    Map<String, BigDecimal> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Assertions.assertEquals(Collections.emptyMap(), portfolio);
  }

  @Test
  void formPortfolio_EmptyPricesNonEmptyModelPortfolioAndCash_EmptyMap() {
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

    Assertions.assertEquals(Collections.singletonMap("USD", BigDecimal.ONE), portfolio);
  }

  @Test
  void formPortfolio_ModelPortfolioWithUniqueProportion_MapWithUniquePosition() {
    Map<String, BigDecimal> modelPortfolio = Collections.singletonMap("MSFT", BigDecimal.ONE);
    Map<String, BigDecimal> prices = Collections.singletonMap("MSFT", BigDecimal.ONE);
    BigDecimal cashValue = BigDecimal.ONE;

    Map<String, BigDecimal> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Assertions.assertEquals(Collections.singletonMap("MSFT", BigDecimal.ONE), portfolio);
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
    expectedPortfolio.put("USD", BigDecimal.ONE);
    Assertions.assertEquals(expectedPortfolio, portfolio);
  }

}
