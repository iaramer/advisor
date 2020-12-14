package ai.adv.portfoliomanager.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModelPortfolioDto {

  private String baseCurrency;

  private Map<String, String> modelPortfolio;

  private String cashValue;

}
