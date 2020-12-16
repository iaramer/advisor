package ai.adv.portfoliomanager.service;

import ai.adv.portfoliomanager.dto.ModelPortfolioDto;
import balancing.Balancer;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioManagementService {

  public Map<String, BigDecimal> formPortfolio(ModelPortfolioDto modelPortfolioDto) {

    Map<String, BigDecimal> modelPortfolio = modelPortfolioDto.getModelPortfolio();
    Map<String, BigDecimal> prices = new HashMap<>();
    prices.put("FXTB", new BigDecimal("734"));
    prices.put("FXGD", new BigDecimal("879"));
    prices.put("FXUS", new BigDecimal("4789"));
    BigDecimal cashValue  = modelPortfolioDto.getCashValue();

    Balancer balancer = new Balancer(modelPortfolioDto.getBaseCurrency());
    return balancer.formPortfolio(modelPortfolio, prices, cashValue);
  }
}
