package ai.adv.portfoliomanager.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Describes combined position, such as country allocation or sector allocation. May contain both complex and single
 * positions.
 */
@Getter
@Setter
public class ComplexPosition implements Position {

  private String description;

  private List<Position> positions;
}
