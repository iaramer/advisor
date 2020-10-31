package ai.adv.portfoliomanager.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import ai.adv.portfoliomanager.model.Portfolio;
import ai.adv.portfoliomanager.model.position.ClientCashPosition;
import ai.adv.portfoliomanager.model.position.ClientSimplePosition;
import ai.adv.portfoliomanager.model.position.ModelSimplePosition;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BalancingServiceTest {

  private static final String PORTFOLIO_NAME = "Portfolio name";

  private BalancingService balancingService;

  @Mock
  private MockDataService dataService;

  @BeforeEach
  void setUp() {
    balancingService = new BalancingService(dataService);
  }

  private static void assertPortfoliosEqual(Portfolio clientPortfolioOnlyCashPosition,
      Portfolio resultClientPortfolio) {
    Assertions.assertFalse(resultClientPortfolio.isModel());
    Assertions.assertEquals(clientPortfolioOnlyCashPosition.getName(), resultClientPortfolio.getName());
    Assertions.assertEquals(clientPortfolioOnlyCashPosition.getBaseCurrency(),
        resultClientPortfolio.getBaseCurrency());
    Assertions.assertEquals(clientPortfolioOnlyCashPosition.getTickers(), resultClientPortfolio.getTickers());
    Assertions.assertEquals(clientPortfolioOnlyCashPosition.getSharesWithNumbers(),
        resultClientPortfolio.getSharesWithNumbers());
  }

  @Test
  void formClientPortfolio_NoPositionsNoCash_EmptyPortfolio() {
    Portfolio modelPortfolioOnlyOneSimplePosition = Portfolio.builder()
        .isModel(true)
        .baseCurrency("RUB")
        .name(PORTFOLIO_NAME)
        .build();
    Portfolio clientPortfolioOnlyOneSimplePosition = Portfolio.builder()
        .isModel(false)
        .baseCurrency("RUB")
        .name(PORTFOLIO_NAME)
        .build();

    when(dataService.getPricesByTickers(anyList(), any())).thenReturn(new HashMap<>());
    BigDecimal cashValue = BigDecimal.ZERO;

    Portfolio resultClientPortfolio = balancingService.formClientPortfolio(modelPortfolioOnlyOneSimplePosition,
        cashValue, clientPortfolioOnlyOneSimplePosition.getName());

    assertPortfoliosEqual(clientPortfolioOnlyOneSimplePosition, resultClientPortfolio);
  }

  @Test
  void formClientPortfolio_OnlyCashPosition_TruePortfolio() {
    ModelSimplePosition modelSimplePosition = ModelSimplePosition.builder()
        .proportion(BigDecimal.ONE)
        .ticker("RUB")
        .build();
    Portfolio modelPortfolioOnlyOneSimplePosition = Portfolio.builder()
        .isModel(true)
        .baseCurrency("RUB")
        .name(PORTFOLIO_NAME)
        .positions(Collections.singletonList(modelSimplePosition))
        .build();

    BigDecimal clientCashPositionSize = new BigDecimal("100");
    ClientCashPosition clientCashPosition = ClientCashPosition.builder()
        .size(clientCashPositionSize)
        .ticker("RUB")
        .build();
    Portfolio clientPortfolioOnlyCashPosition = Portfolio.builder()
        .isModel(false)
        .baseCurrency("RUB")
        .name(PORTFOLIO_NAME)
        .positions(Collections.singletonList(clientCashPosition))
        .build();

    Map<String, BigDecimal> pricesOfPortfolioShares = new HashMap<>();
    when(dataService.getPricesByTickers(anyList(), any())).thenReturn(pricesOfPortfolioShares);

    Portfolio resultClientPortfolio = balancingService.formClientPortfolio(modelPortfolioOnlyOneSimplePosition,
        clientCashPositionSize, clientPortfolioOnlyCashPosition.getName());

    assertPortfoliosEqual(clientPortfolioOnlyCashPosition, resultClientPortfolio);
  }

  @Test
  void formClientPortfolio_OnlyOneSimplePosition_TruePortfolio() {
    ModelSimplePosition modelSimplePosition = ModelSimplePosition.builder()
        .proportion(BigDecimal.ONE)
        .ticker("FXIT")
        .build();
    Portfolio modelPortfolioOnlyOneSimplePosition = Portfolio.builder()
        .isModel(true)
        .baseCurrency("RUB")
        .name(PORTFOLIO_NAME)
        .positions(Collections.singletonList(modelSimplePosition))
        .build();

    int clientSimplePositionSize = 10;
    BigDecimal FXITprice = new BigDecimal("5000.00");
    ClientSimplePosition clientSimplePosition = ClientSimplePosition.builder()
        .size(clientSimplePositionSize)
        .ticker("FXIT")
        .build();
    Portfolio clientPortfolioOnlyOneSimplePosition = Portfolio.builder()
        .isModel(false)
        .baseCurrency("RUB")
        .name(PORTFOLIO_NAME)
        .positions(Collections.singletonList(clientSimplePosition))
        .build();

    Map<String, BigDecimal> pricesOfPortfolioShares = new HashMap<>();
    pricesOfPortfolioShares.put("FXIT", FXITprice);
    when(dataService.getPricesByTickers(anyList(), any())).thenReturn(pricesOfPortfolioShares);
    BigDecimal cashValue = FXITprice.multiply(new BigDecimal(clientSimplePositionSize));

    Portfolio resultClientPortfolio = balancingService.formClientPortfolio(modelPortfolioOnlyOneSimplePosition,
        cashValue, clientPortfolioOnlyOneSimplePosition.getName());

    assertPortfoliosEqual(clientPortfolioOnlyOneSimplePosition, resultClientPortfolio);
  }

  @Test
  void formClientPortfolio_TwoSimplePositionsSameNames_TruePortfolio() {
    ModelSimplePosition modelSimplePosition1 = ModelSimplePosition.builder()
        .proportion(new BigDecimal("0.5"))
        .ticker("AIIT")
        .build();
    ModelSimplePosition modelSimplePosition2 = ModelSimplePosition.builder()
        .proportion(new BigDecimal("0.5"))
        .ticker("AIIT")
        .build();
    Portfolio modelPortfolioOnlyOneSimplePosition = Portfolio.builder()
        .isModel(true)
        .baseCurrency("RUB")
        .name(PORTFOLIO_NAME)
        .positions(List.of(modelSimplePosition1, modelSimplePosition2))
        .build();

    int clientSimplePositionSize = 2;
    BigDecimal AIITprice = new BigDecimal("5");
    ClientSimplePosition clientSimplePosition = ClientSimplePosition.builder()
        .size(clientSimplePositionSize)
        .ticker("AIIT")
        .build();
    Portfolio clientPortfolioOnlyOneSimplePosition = Portfolio.builder()
        .isModel(false)
        .baseCurrency("RUB")
        .name(PORTFOLIO_NAME)
        .positions(Collections.singletonList(clientSimplePosition))
        .build();

    Map<String, BigDecimal> pricesOfPortfolioShares = new HashMap<>();
    pricesOfPortfolioShares.put("AIIT", AIITprice);
    when(dataService.getPricesByTickers(anyList(), any())).thenReturn(pricesOfPortfolioShares);
    BigDecimal cashValue = new BigDecimal("10");

    Portfolio resultClientPortfolio = balancingService.formClientPortfolio(modelPortfolioOnlyOneSimplePosition,
        cashValue, clientPortfolioOnlyOneSimplePosition.getName());

    assertPortfoliosEqual(clientPortfolioOnlyOneSimplePosition, resultClientPortfolio);
  }

  @Test
  void formClientPortfolio_OneSimplePositionsAndCash_TruePortfolio() {
    ModelSimplePosition modelSimplePosition = ModelSimplePosition.builder()
        .proportion(new BigDecimal("0.5"))
        .ticker("AIIT")
        .build();
    ModelSimplePosition modelCashPosition = ModelSimplePosition.builder()
        .proportion(new BigDecimal("0.5"))
        .ticker("RUB")
        .build();
    Portfolio modelPortfolio = Portfolio.builder()
        .isModel(true)
        .baseCurrency("RUB")
        .name(PORTFOLIO_NAME)
        .positions(List.of(modelSimplePosition, modelCashPosition))
        .build();

    BigDecimal AIITprice = new BigDecimal("4500");
    ClientSimplePosition clientSimplePosition = ClientSimplePosition.builder()
        .size(1)
        .ticker("AIIT")
        .build();
    ClientCashPosition clientCashPosition = ClientCashPosition.builder()
        .size(new BigDecimal("5500"))
        .ticker("RUB")
        .build();
    Portfolio clientPortfolioOnlyOneSimplePosition = Portfolio.builder()
        .isModel(false)
        .baseCurrency("RUB")
        .name(PORTFOLIO_NAME)
        .positions(List.of(clientSimplePosition, clientCashPosition))
        .build();

    Map<String, BigDecimal> pricesOfPortfolioShares = new HashMap<>();
    pricesOfPortfolioShares.put("AIIT", AIITprice);
    when(dataService.getPricesByTickers(anyList(), any())).thenReturn(pricesOfPortfolioShares);
    BigDecimal cashValue = new BigDecimal("10000");

    Portfolio resultClientPortfolio = balancingService.formClientPortfolio(modelPortfolio,
        cashValue, clientPortfolioOnlyOneSimplePosition.getName());

    assertPortfoliosEqual(clientPortfolioOnlyOneSimplePosition, resultClientPortfolio);
  }

  @Test
  void formClientPortfolio_TwoSimplePositionsAndAutoAddedCashRemainder_TruePortfolio() {
    ModelSimplePosition modelSimplePosition1 = ModelSimplePosition.builder()
        .proportion(new BigDecimal("0.5"))
        .ticker("AIIT")
        .build();
    ModelSimplePosition modelSimplePosition2 = ModelSimplePosition.builder()
        .proportion(new BigDecimal("0.5"))
        .ticker("AISP")
        .build();
    Portfolio modelPortfolio = Portfolio.builder()
        .isModel(true)
        .baseCurrency("RUB")
        .name(PORTFOLIO_NAME)
        .positions(List.of(modelSimplePosition1, modelSimplePosition2))
        .build();

    BigDecimal AIITprice = new BigDecimal("5500.00");
    ClientSimplePosition clientSimplePosition1 = ClientSimplePosition.builder()
        .size(1)
        .ticker("AIIT")
        .build();
    BigDecimal AISPprice = new BigDecimal("4000.00");
    ClientSimplePosition clientSimplePosition2 = ClientSimplePosition.builder()
        .size(1)
        .ticker("AISP")
        .build();
    ClientCashPosition clientCashPosition = ClientCashPosition.builder()
        .size(new BigDecimal("500.00"))
        .ticker("RUB")
        .build();
    Portfolio clientPortfolioOnlyOneSimplePosition = Portfolio.builder()
        .isModel(false)
        .baseCurrency("RUB")
        .name(PORTFOLIO_NAME)
        .positions(List.of(clientSimplePosition1, clientSimplePosition2, clientCashPosition))
        .build();

    Map<String, BigDecimal> pricesOfPortfolioShares = new HashMap<>();
    pricesOfPortfolioShares.put("AIIT", AIITprice);
    pricesOfPortfolioShares.put("AISP", AISPprice);
    when(dataService.getPricesByTickers(anyList(), any())).thenReturn(pricesOfPortfolioShares);
    BigDecimal cashValue = new BigDecimal("10000");

    Portfolio resultClientPortfolio = balancingService.formClientPortfolio(modelPortfolio,
        cashValue, clientPortfolioOnlyOneSimplePosition.getName());

    assertPortfoliosEqual(clientPortfolioOnlyOneSimplePosition, resultClientPortfolio);
  }

  @Disabled("Disabled until complex positions are handled in formClientPortfolio method")
  @Test
  void formClientPortfolio_SimpleModelPortfolio_TruePortfolio() {
    // todo: add check of structure
  }
}