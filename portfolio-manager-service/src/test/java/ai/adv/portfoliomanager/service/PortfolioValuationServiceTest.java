package ai.adv.portfoliomanager.service;

import ai.adv.portfoliomanager.model.Portfolio;
import ai.adv.portfoliomanager.model.position.ClientCashPosition;
import ai.adv.portfoliomanager.model.position.ClientSimplePosition;
import ai.adv.portfoliomanager.model.position.ComplexPosition;
import ai.adv.portfoliomanager.model.position.Position;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PortfolioValuationServiceTest {

  private static final String PORTFOLIO_NAME = "Portfolio name";

  private Map<String, BigDecimal> sharesPrices;
  private PortfolioValuationService portfolioValuationService;

  @BeforeEach
  void setUp() {
    sharesPrices = new HashMap<>();
    portfolioValuationService = new PortfolioValuationService();
  }

  @Test
  void getPortfolioValue_EmptySharesPrices_IllegalArgumentException() {
    Map<String, BigDecimal> emptySharesPrices = Collections.emptyMap();
    Portfolio portfolio = Portfolio.builder().build();

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> portfolioValuationService.getPortfolioValue(portfolio, emptySharesPrices));
  }

  @Test
  void getPortfolioValue_ModelPortfolio_UnsupportedOperationException() {
    Portfolio modelPortfolio = Portfolio.builder()
        .baseCurrency("USD")
        .isModel(true)
        .name(PORTFOLIO_NAME)
        .positions(Collections.emptyList())
        .build();

    Assertions.assertThrows(UnsupportedOperationException.class,
        () -> portfolioValuationService.getPortfolioValue(modelPortfolio, sharesPrices));
  }

  @Test
  void getPortfolioValue_EmptyPortfolio_Zero() {
    sharesPrices.put("AIIT", new BigDecimal("5000.00"));

    Portfolio portfolio = Portfolio.builder().build();

    BigDecimal portfolioValue = portfolioValuationService.getPortfolioValue(portfolio, sharesPrices);

    Assertions.assertEquals(BigDecimal.ZERO, portfolioValue);
  }

  @Test
  void getPortfolioValue_PortfolioWithSimplePositionsWithoutCash_TrueValue() {
    sharesPrices.put("AIIT", new BigDecimal("5000.00"));
    sharesPrices.put("AICB", new BigDecimal("1250.00"));
    sharesPrices.put("AIGD", new BigDecimal("50.00"));

    List<Position> positions = new ArrayList<>();
    positions.add(ClientSimplePosition.builder()
        .ticker("AIIT")
        .size(1)
        .build());
    positions.add(ClientSimplePosition.builder()
        .ticker("AICB")
        .size(4)
        .build());
    positions.add(ClientSimplePosition.builder()
        .ticker("AIGD")
        .size(100)
        .build());

    Portfolio clientPortfolioOnlySimplePositions = Portfolio.builder()
        .baseCurrency("RUB")
        .isModel(false)
        .name(PORTFOLIO_NAME)
        .positions(positions)
        .build();

    BigDecimal clientPortfolioValue = new BigDecimal("15000.00");

    BigDecimal resultPortfolioValue = portfolioValuationService
        .getPortfolioValue(clientPortfolioOnlySimplePositions, sharesPrices);

    Assertions.assertEquals(clientPortfolioValue, resultPortfolioValue);
  }

  @Test
  void getPortfolioValue_PortfolioWithSimplePositionsWithCash_TrueValue() {
    sharesPrices.put("AIIT", new BigDecimal("5000.00"));
    sharesPrices.put("AICB", new BigDecimal("1250.00"));
    sharesPrices.put("AIGD", new BigDecimal("1000.00"));
    sharesPrices.put("USD", new BigDecimal("250.00"));

    List<Position> positions = new ArrayList<>();
    positions.add(ClientSimplePosition.builder()
        .ticker("AIIT")
        .size(1)
        .build());
    positions.add(ClientSimplePosition.builder()
        .ticker("AICB")
        .size(4)
        .build());
    positions.add(ClientSimplePosition.builder()
        .ticker("AIGD")
        .size(5)
        .build());
    positions.add(ClientCashPosition.builder()
        .ticker("USD")
        .size(new BigDecimal("20.00"))
        .build());
    positions.add(ClientCashPosition.builder()
        .ticker("RUB")
        .size(new BigDecimal("200"))
        .build());

    Portfolio clientPortfolioOnlySimplePositions = Portfolio.builder()
        .baseCurrency("RUB")
        .isModel(false)
        .name(PORTFOLIO_NAME)
        .positions(positions)
        .build();

    BigDecimal clientPortfolioValue = new BigDecimal("20200.00");

    BigDecimal resultPortfolioValue = portfolioValuationService
        .getPortfolioValue(clientPortfolioOnlySimplePositions, sharesPrices);

    Assertions.assertEquals(clientPortfolioValue, resultPortfolioValue);
  }

  @Test
  void getPortfolioValue_PortfolioWithComplexPositionWithoutCash_TrueValue() {
    sharesPrices.put("AIIT", new BigDecimal("5000.00"));
    sharesPrices.put("AISP", new BigDecimal("1250.00"));
    sharesPrices.put("AIGD", new BigDecimal("50.00"));

    ComplexPosition complexPosition = new ComplexPosition("Stocks");
    complexPosition.addPosition(ClientSimplePosition.builder()
        .ticker("AIIT")
        .size(1)
        .build());
    complexPosition.addPosition(ClientSimplePosition.builder()
        .ticker("AISP")
        .size(4)
        .build());

    List<Position> positions = new ArrayList<>();
    positions.add(complexPosition);
    positions.add(ClientSimplePosition.builder()
        .ticker("AIGD")
        .size(100)
        .build());

    Portfolio clientPortfolioWithComplexPosition = Portfolio.builder()
        .baseCurrency("RUB")
        .isModel(false)
        .name(PORTFOLIO_NAME)
        .positions(positions)
        .build();

    BigDecimal clientPortfolioValue = new BigDecimal("15000.00");

    BigDecimal resultPortfolioValue = portfolioValuationService
        .getPortfolioValue(clientPortfolioWithComplexPosition, sharesPrices);

    Assertions.assertEquals(clientPortfolioValue, resultPortfolioValue);
  }

}