package balancing;

import java.math.BigDecimal;
import java.util.Collections;
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

    Map<String, Integer> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Assertions.assertEquals(Collections.emptyMap(), portfolio);
  }

  @Test
  void formPortfolio_EmptyModelPortfolioNonZeroPricesAndCash_EmptyMap() {
    Map<String, BigDecimal> modelPortfolio = Collections.emptyMap();
    Map<String, BigDecimal> prices = Collections.singletonMap("USD", BigDecimal.ONE);
    BigDecimal cashValue = BigDecimal.ONE;

    Map<String, Integer> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Assertions.assertEquals(Collections.emptyMap(), portfolio);
  }

  @Test
  void formPortfolio_EmptyPricesNonEmptyModelPortfolioAndCash_EmptyMap() {
    Map<String, BigDecimal> modelPortfolio = Collections.singletonMap("USD", BigDecimal.ONE);
    Map<String, BigDecimal> prices = Collections.emptyMap();
    BigDecimal cashValue = BigDecimal.ONE;

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> balancer.formPortfolio(modelPortfolio, prices, cashValue));
  }

  @Test
  void formPortfolio_ModelPortfolioWithZeroProportion_EmptyMap() {
    Map<String, BigDecimal> modelPortfolio = Collections.singletonMap("USD", BigDecimal.ZERO);
    Map<String, BigDecimal> prices = Collections.singletonMap("USD", BigDecimal.ONE);
    BigDecimal cashValue = BigDecimal.ONE;

    Map<String, Integer> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Assertions.assertEquals(Collections.emptyMap(), portfolio);
  }

  @Test
  void formPortfolio_ModelPortfolioWithUniqueProportion_MapWithUniquePosition() {
    Map<String, BigDecimal> modelPortfolio = Collections.singletonMap("MSFT", BigDecimal.ONE);
    Map<String, BigDecimal> prices = Collections.singletonMap("MSFT", BigDecimal.ONE);
    BigDecimal cashValue = BigDecimal.ONE;

    Map<String, Integer> portfolio = balancer.formPortfolio(modelPortfolio, prices, cashValue);

    Assertions.assertEquals(Collections.singletonMap("MSFT", 1), portfolio);
  }

}
