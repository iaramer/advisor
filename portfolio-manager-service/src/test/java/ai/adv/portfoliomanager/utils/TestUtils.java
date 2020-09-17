package ai.adv.portfoliomanager.utils;

import ai.adv.portfoliomanager.model.Portfolio;
import ai.adv.portfoliomanager.model.position.ClientSimplePosition;
import ai.adv.portfoliomanager.model.position.ComplexPosition;
import ai.adv.portfoliomanager.model.position.ModelSimplePosition;
import ai.adv.portfoliomanager.model.position.Position;
import ai.adv.portfoliomanager.model.position.PositionType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUtils {

  private static final String FXIT = "FXIT";
  private static final String SBSP = "SBSP";
  private static final String SBCB = "SBCB";
  private static final String FXGD = "FXGD";

  private static final BigDecimal FXIT_PRICE = new BigDecimal("5000.11");
  private static final BigDecimal SBSP_PRICE = new BigDecimal("1251.00");
  private static final BigDecimal SBCB_PRICE = new BigDecimal("2500.00");
  private static final BigDecimal FXGD_PRICE = new BigDecimal("75.00");

  private static final String BASE_CURRENCY = "RUB";
  private static final String PORTFOLIO_NAME = "Portfolio name";
  private static final BigDecimal PORTFOLIO_VALUE = new BigDecimal("250000.00");

  private static final ModelSimplePosition modelSimplePositionFXIT = ModelSimplePosition.builder()
      .ticker(FXIT)
      .proportion(new BigDecimal("0.4"))
      .positionType(PositionType.MODEL_SIMPLE_POSITION)
      .build();
  private static final ModelSimplePosition modelSimplePositionSBCB = ModelSimplePosition.builder()
      .ticker(SBCB)
      .proportion(new BigDecimal("0.3"))
      .positionType(PositionType.MODEL_SIMPLE_POSITION)
      .build();
  private static final ModelSimplePosition modelSimplePositionSBSP = ModelSimplePosition.builder()
      .ticker(SBSP)
      .proportion(new BigDecimal("0.15"))
      .positionType(PositionType.MODEL_SIMPLE_POSITION)
      .build();
  private static final ModelSimplePosition modelSimplePositionFXGD = ModelSimplePosition.builder()
      .ticker(FXGD)
      .proportion(new BigDecimal("0.15"))
      .positionType(PositionType.MODEL_SIMPLE_POSITION)
      .build();

  private static final ClientSimplePosition clientSimplePositionFXIT = ClientSimplePosition.builder()
      .ticker(FXIT)
      .size(20)
      .positionType(PositionType.CLIENT_SIMPLE_POSITION)
      .build();
  private static final ClientSimplePosition clientSimplePositionSBSP = ClientSimplePosition.builder()
      .ticker(SBSP)
      .size(30)
      .positionType(PositionType.CLIENT_SIMPLE_POSITION)
      .build();
  private static final ClientSimplePosition clientSimplePositionSBCB = ClientSimplePosition.builder()
      .ticker(SBCB)
      .size(30)
      .positionType(PositionType.CLIENT_SIMPLE_POSITION)
      .build();
  private static final ClientSimplePosition clientSimplePositionFXGD = ClientSimplePosition.builder()
      .ticker(FXGD)
      .size(50)
      .positionType(PositionType.CLIENT_SIMPLE_POSITION)
      .build();

  public static Map<String, BigDecimal> getTestSharesPrices() {

    Map<String, BigDecimal> pricesOfPortfolioShares = new HashMap<>();
    pricesOfPortfolioShares.put(FXIT, FXIT_PRICE);
    pricesOfPortfolioShares.put(SBSP, SBSP_PRICE);
    pricesOfPortfolioShares.put(SBCB, SBCB_PRICE);
    pricesOfPortfolioShares.put(FXGD, FXGD_PRICE);

    return pricesOfPortfolioShares;
  }

  public static BigDecimal getTestClientPortfolioOnlySimplePositionsValue() {
    return PORTFOLIO_VALUE;
  }

  public static BigDecimal getTestClientPortfolioWithComplexPositionsValue() {
    return PORTFOLIO_VALUE;
  }

  public static Portfolio getTestModelPortfolioOnlySimplePositions() {
    List<Position> positions = new ArrayList<>();
    positions.add(modelSimplePositionFXIT);
    positions.add(modelSimplePositionSBSP);
    positions.add(modelSimplePositionSBCB);
    positions.add(modelSimplePositionFXGD);

    return Portfolio.builder()
        .baseCurrency(BASE_CURRENCY)
        .isModel(true)
        .name(PORTFOLIO_NAME)
        .positions(positions)
        .build();
  }

  public static Portfolio getTestModelPortfolioWithComplexPositions() {
    List<Position> positions = new ArrayList<>();

    ComplexPosition complexPosition = new ComplexPosition();
    complexPosition.setPositionType(PositionType.COMPLEX_POSITION);
    complexPosition.addPosition(modelSimplePositionFXIT);
    complexPosition.addPosition(modelSimplePositionSBSP);

    positions.add(complexPosition);
    positions.add(modelSimplePositionSBCB);
    positions.add(modelSimplePositionFXGD);

    return Portfolio.builder()
        .baseCurrency(BASE_CURRENCY)
        .isModel(false)
        .name(PORTFOLIO_NAME)
        .positions(positions)
        .build();
  }

  public static Portfolio getTestClientPortfolioOnlySimplePositions() {
    List<Position> positions = new ArrayList<>();
    positions.add(clientSimplePositionFXIT);
    positions.add(clientSimplePositionSBSP);
    positions.add(clientSimplePositionSBCB);
    positions.add(clientSimplePositionFXGD);

    return Portfolio.builder()
        .baseCurrency(BASE_CURRENCY)
        .isModel(false)
        .name(PORTFOLIO_NAME)
        .positions(positions)
        .build();
  }

  public static Portfolio getTestClientPortfolioWithComplexPositions() {
    List<Position> positions = new ArrayList<>();

    ComplexPosition complexPosition = new ComplexPosition();
    complexPosition.addPosition(clientSimplePositionFXIT);
    complexPosition.addPosition(clientSimplePositionSBSP);

    positions.add(complexPosition);
    positions.add(clientSimplePositionSBCB);
    positions.add(clientSimplePositionFXGD);

    return Portfolio.builder()
        .baseCurrency(BASE_CURRENCY)
        .isModel(false)
        .name(PORTFOLIO_NAME)
        .positions(positions)
        .build();
  }
}
