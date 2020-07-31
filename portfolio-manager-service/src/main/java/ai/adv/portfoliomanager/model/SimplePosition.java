package ai.adv.portfoliomanager.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Describes simple allocation, such as share or ETF. Could be a part of ComplexPosition.
 *
 * @See ComplexPosition
 */
@Getter
@Setter
public class SimplePosition implements Position {

  private String ticker;

  private int size;
}
