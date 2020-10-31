package ai.adv.portfoliomanager.service;

import ai.adv.portfoliomanager.model.Portfolio;
import ai.adv.portfoliomanager.model.position.ClientCashPosition;
import ai.adv.portfoliomanager.model.position.ClientSimplePosition;
import ai.adv.portfoliomanager.model.position.ModelSimplePosition;
import ai.adv.portfoliomanager.model.position.Position;
import ai.adv.portfoliomanager.model.position.PositionType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalancingService {

  private final MockDataService dataService;

  public Portfolio formClientPortfolio(Portfolio modelPortfolio, BigDecimal cashValue, String portfolioName) {
    BigDecimal valueRemainder = cashValue;
    Map<String, BigDecimal> modelPortfolioSecuritiesPrices = getPricesOfPortfolioShares(modelPortfolio);
    List<ModelSimplePosition> modelPortfolioPositionsDesc = getModelPortfolioPositionsDesc(modelPortfolio);

    List<Position> positions = new ArrayList<>();
    for (ModelSimplePosition modelSimplePosition : modelPortfolioPositionsDesc) {
      String ticker = modelSimplePosition.getTicker();

      // todo: add forming of complex positions - do recursion to handle complex transactions
      BigDecimal exactPositionValue = modelSimplePosition.getProportion().multiply(cashValue);
      BigDecimal stockPrice = modelPortfolioSecuritiesPrices.get(ticker);
      BigDecimal numberOfSecurities = exactPositionValue.divide(stockPrice, RoundingMode.FLOOR);

      ClientSimplePosition newPosition = getClientSimplePosition(ticker, numberOfSecurities);
      valueRemainder = valueRemainder.subtract(newPosition.getPositionValue(stockPrice));
      addNewClientSimplePosition(positions, newPosition);
    }

    for (ModelSimplePosition modelSimplePosition : modelPortfolioPositionsDesc) {
      String ticker = modelSimplePosition.getTicker();

      BigDecimal stockPrice = modelPortfolioSecuritiesPrices.get(ticker);
      BigDecimal numberOfSecurities = valueRemainder.divide(stockPrice, RoundingMode.FLOOR)
          .setScale(0, RoundingMode.DOWN);
      if (numberOfSecurities.compareTo(BigDecimal.ZERO) <= 0) {
        continue;
      }

      ClientSimplePosition newPosition = getClientSimplePosition(ticker, numberOfSecurities);
      valueRemainder = valueRemainder.subtract(newPosition.getPositionValue(stockPrice));
      addNewClientSimplePosition(positions, newPosition);
    }

    addNewClientCashPosition(positions, getClientCashPosition(modelPortfolio, valueRemainder));

    return Portfolio.builder()
        .name(portfolioName)
        .isModel(false)
        .baseCurrency(modelPortfolio.getBaseCurrency())
        .positions(positions)
        .build();
  }

  private void addNewClientCashPosition(List<Position> positions, ClientCashPosition newPosition) {
    if (newPosition.getSize().compareTo(BigDecimal.ZERO) < 1) {
      return;
    }
    positions.stream()
        .filter(position -> position.getPositionType() == PositionType.CLIENT_CASH_POSITION)
        .map(position -> (ClientCashPosition) position)
        .filter(position -> position.getTicker().equals(newPosition.getTicker()))
        .reduce(this::sumClientCashPosition)
        .ifPresentOrElse(position -> position.addSize(newPosition.getSize()), () -> positions.add(newPosition));
  }

  @Nonnull
  private ClientCashPosition sumClientCashPosition(ClientCashPosition p1, ClientCashPosition p2) {
    p1.addSize(p2.getSize());
    return p1;
  }

  private void addNewClientSimplePosition(List<Position> positions, ClientSimplePosition newPosition) {
    positions.stream()
        .filter(position -> position.getPositionType() == PositionType.CLIENT_SIMPLE_POSITION)
        .map(position -> (ClientSimplePosition) position)
        .filter(position -> position.getTicker().equals(newPosition.getTicker()))
        .reduce(this::sumClientSimplePositions)
        .ifPresentOrElse(position -> position.addSize(newPosition.getSize()), () -> positions.add(newPosition));
  }

  @Nonnull
  private ClientSimplePosition sumClientSimplePositions(ClientSimplePosition p1, ClientSimplePosition p2) {
    p1.addSize(p2.getSize());
    return p1;
  }

  private ClientCashPosition getClientCashPosition(Portfolio modelPortfolio, BigDecimal valueRemainder) {
    return ClientCashPosition.builder()
        .size(valueRemainder)
        .ticker(modelPortfolio.getBaseCurrency())
        .build();
  }

  private ClientSimplePosition getClientSimplePosition(String ticker, BigDecimal numberOfSecurities) {
    return ClientSimplePosition.builder()
        .size(numberOfSecurities.intValue())
        .ticker(ticker)
        .build();
  }

  private List<ModelSimplePosition> getModelPortfolioPositionsDesc(Portfolio modelPortfolio) {
    return modelPortfolio.getPositions().stream()
        .map(position -> (ModelSimplePosition) position)
        .sorted(Comparator.comparing(ModelSimplePosition::getProportion).reversed())
        .collect(Collectors.toList());
  }

  private Map<String, BigDecimal> getPricesOfPortfolioShares(Portfolio portfolio) {
    Map<String, BigDecimal> prices = dataService
        .getPricesByTickers(portfolio.getTickers(), portfolio.getBaseCurrency());
    prices.put(portfolio.getBaseCurrency(), BigDecimal.ONE);
    return prices;
  }

}
