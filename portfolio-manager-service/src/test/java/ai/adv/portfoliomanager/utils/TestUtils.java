package ai.adv.portfoliomanager.utils;

import ai.adv.portfoliomanager.model.Portfolio;
import ai.adv.portfoliomanager.model.position.ClientSimplePosition;
import ai.adv.portfoliomanager.model.position.ComplexPosition;
import ai.adv.portfoliomanager.model.position.ModelSimplePosition;
import ai.adv.portfoliomanager.model.position.Position;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUtils {

  public static Map<String, BigDecimal> getTestSharesPrices() {

    Map<String, BigDecimal> pricesOfPortfolioShares = new HashMap<>();
    pricesOfPortfolioShares.put("FXIT", new BigDecimal("5456.12"));
    pricesOfPortfolioShares.put("SBSP", new BigDecimal("1274.78"));
    pricesOfPortfolioShares.put("SBCB", new BigDecimal("1077.23"));
    pricesOfPortfolioShares.put("FXGD", new BigDecimal("778.56"));

    return pricesOfPortfolioShares;
  }

  public static Portfolio getTestModelPortfolio() {
    List<Position> positions = new ArrayList<>();

    positions.add(ModelSimplePosition.builder()
        .ticker("FXIT")
        .proportion(new BigDecimal("0.4"))
        .build());

    positions.add(ModelSimplePosition.builder()
        .ticker("SBSP")
        .proportion(new BigDecimal("0.3"))
        .build());

    positions.add(ModelSimplePosition.builder()
        .ticker("SBCB")
        .proportion(new BigDecimal("0.2"))
        .build());

    positions.add(ModelSimplePosition.builder()
        .ticker("SBCB")
        .proportion(new BigDecimal("0.1"))
        .build());

    return Portfolio.builder()
        .baseCurrency("RUB")
        .isModel(true)
        .name("Test model portfolio")
        .positions(positions)
        .build();
  }

  public static BigDecimal getTestClientPortfolioOnlySimplePositionsValue() {
    return new BigDecimal("161276.75");
  }

  public static Portfolio getTestClientPortfolioOnlySimplePositions() {
    List<Position> positions = new ArrayList<>();

    positions.add(ClientSimplePosition.builder()
        .ticker("FXIT")
        .size(5)
        .build());

    positions.add(ClientSimplePosition.builder()
        .ticker("SBSP")
        .size(45)
        .build());

    positions.add(ClientSimplePosition.builder()
        .ticker("SBCB")
        .size(35)
        .build());

    positions.add(ClientSimplePosition.builder()
        .ticker("FXGD")
        .size(50)
        .build());

    return Portfolio.builder()
        .baseCurrency("RUB")
        .isModel(false)
        .name("Test client portfolio")
        .positions(positions)
        .build();
  }

  public static BigDecimal getTestClientPortfolioWithComplexPositionsValue() {
    return new BigDecimal("161276.75");
  }

  public static Portfolio getTestClientPortfolioWithComplexPositions() {
    List<Position> positions = new ArrayList<>();

    ComplexPosition complexPosition = new ComplexPosition();

    complexPosition.addPosition(ClientSimplePosition.builder()
        .ticker("FXIT")
        .size(5)
        .build());

    complexPosition.addPosition(ClientSimplePosition.builder()
        .ticker("SBSP")
        .size(45)
        .build());

    positions.add(complexPosition);

    positions.add(ClientSimplePosition.builder()
        .ticker("SBCB")
        .size(35)
        .build());

    positions.add(ClientSimplePosition.builder()
        .ticker("FXGD")
        .size(50)
        .build());

    return Portfolio.builder()
        .baseCurrency("RUB")
        .isModel(false)
        .name("Test client portfolio")
        .positions(positions)
        .build();
  }
}
