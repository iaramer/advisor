package ai.adv.portfoliomanager.service;

import ai.adv.portfoliomanager.model.Portfolio;
import ai.adv.portfoliomanager.utils.TestUtils;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PortfolioValuationServiceTest {

  private Map<String, BigDecimal> sharesPrices;

  private PortfolioValuationService portfolioValuationService;

  @BeforeEach
  void setUp() {
    sharesPrices = TestUtils.getTestSharesPrices();

    portfolioValuationService = new PortfolioValuationService();
  }

  @Test
  void getPortfolioValue_EmptySharesPrices_IllegalArgumentException() {
    Map<String, BigDecimal> emptySharesPrices = Collections.emptyMap();
    Portfolio portfolio = Portfolio.empty();

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> portfolioValuationService.getPortfolioValue(portfolio, emptySharesPrices));
  }

  @Test
  void getPortfolioValue_ModelPortfolio_UnsupportedOperationException() {
    Portfolio modelPortfolio = TestUtils.getTestModelPortfolioOnlySimplePositions();

    Assertions.assertThrows(UnsupportedOperationException.class,
        () -> portfolioValuationService.getPortfolioValue(modelPortfolio, sharesPrices));
  }

  @Test
  void getPortfolioValue_EmptyPortfolio_Zero() {
    Portfolio portfolio = Portfolio.empty();

    BigDecimal portfolioValue = portfolioValuationService.getPortfolioValue(portfolio, sharesPrices);

    Assertions.assertEquals(BigDecimal.ZERO, portfolioValue);
  }

  @Test
  void getPortfolioValue_PortfolioWithSimplePositions_TrueValue() {
    Portfolio clientPortfolioOnlySimplePositions = TestUtils.getTestClientPortfolioOnlySimplePositions();
    BigDecimal clientPortfolioValue = TestUtils.getTestClientPortfolioOnlySimplePositionsValue();

    BigDecimal resultPortfolioValue = portfolioValuationService
        .getPortfolioValue(clientPortfolioOnlySimplePositions, sharesPrices);

    Assertions.assertEquals(clientPortfolioValue, resultPortfolioValue);
  }

  @Test
  void getPortfolioValue_PortfolioWithComplexPositions_TrueValue() {
    Portfolio clientPortfolioWithComplexPositions = TestUtils.getTestClientPortfolioWithComplexPositions();
    BigDecimal clientPortfolioValue = TestUtils.getTestClientPortfolioWithComplexPositionsValue();

    BigDecimal resultPortfolioValue = portfolioValuationService
        .getPortfolioValue(clientPortfolioWithComplexPositions, sharesPrices);

    Assertions.assertEquals(clientPortfolioValue, resultPortfolioValue);
  }

}