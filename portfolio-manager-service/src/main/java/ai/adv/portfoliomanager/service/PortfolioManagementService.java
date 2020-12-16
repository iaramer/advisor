package ai.adv.portfoliomanager.service;

import ai.adv.portfoliomanager.dto.ModelPortfolioDto;
import balancing.Balancer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioManagementService {

  private final MockDataService mockDataService;

  public Map<String, BigDecimal> formPortfolio(ModelPortfolioDto modelPortfolioDto) {

    Map<String, BigDecimal> modelPortfolio = modelPortfolioDto.getModelPortfolio();
    List<String> tickers = new ArrayList<>(modelPortfolio.keySet());
    Map<String, BigDecimal> prices = mockDataService.getPricesByTickers(tickers);
    BigDecimal cashValue = modelPortfolioDto.getCashValue();

    Balancer balancer = new Balancer(modelPortfolioDto.getBaseCurrency());
    return balancer.formPortfolio(modelPortfolio, prices, cashValue);
  }
}
