package ai.adv.portfoliomanager.model.position;

import java.util.List;

/**
 * Interface, which describes a position or allocation in portfolio
 */
public interface Position {

  List<String> getTickers();
}
