package ai.adv.portfoliomanager.model.position;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Interface, which describes a position or allocation in portfolio
 */
public interface Position {

  PositionType getPositionType();

  List<String> getTickers();

  Map<String, BigDecimal> getSharesWithNumbers();

  List<Position> getPositions();

  BigDecimal getPositionValue(BigDecimal price);

}
