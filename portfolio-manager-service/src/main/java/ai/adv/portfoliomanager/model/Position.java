package ai.adv.portfoliomanager.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position {

  private String ticker;
  private int size;
  private String currency;

}
