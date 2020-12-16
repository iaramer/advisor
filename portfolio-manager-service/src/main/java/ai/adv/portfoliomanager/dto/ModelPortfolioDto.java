package ai.adv.portfoliomanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
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

  @JsonProperty("base_currency")
  private String baseCurrency;

  @JsonProperty("model_portfolio")
  private Map<String, BigDecimal> modelPortfolio;

  @JsonProperty("cash_value")
  private BigDecimal cashValue;

}
