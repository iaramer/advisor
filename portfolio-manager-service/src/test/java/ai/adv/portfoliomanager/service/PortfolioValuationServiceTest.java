package ai.adv.portfoliomanager.service;

import ai.adv.portfoliomanager.model.Portfolio;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class PortfolioValuationServiceTest {

  private PortfolioValuationService portfolioValuationService;

  @BeforeEach
  void setUp() {
    portfolioValuationService = new PortfolioValuationService();
  }

  @Test
  void getPortfolioValue_EmptySharesPrices_IllegalArgumentException() {
    Map<String, BigDecimal> sharesPrices = Collections.emptyMap();
    Portfolio portfolio = Portfolio.builder()
        .build();

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      portfolioValuationService.getPortfolioValue(portfolio, sharesPrices);
    });
  }

}