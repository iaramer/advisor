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
    Map<String, BigDecimal> modelPortfolio = new HashMap<>();
    Map<String, BigDecimal> prices = new HashMap<>();
    BigDecimal cashValue = BigDecimal.ONE;

    Balancer balancer = new Balancer("USD");
    return balancer.formPortfolio(modelPortfolio, prices, cashValue);
  }
}
