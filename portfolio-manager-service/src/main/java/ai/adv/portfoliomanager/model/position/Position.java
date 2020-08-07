package ai.adv.portfoliomanager.model.position;

import java.util.List;
import java.util.Map;

/**
 * Interface, which describes a position or allocation in portfolio
 */
public interface Position {

  List<String> getTickers();

  Map<String, Integer> getSharesWithNumbers();
}
