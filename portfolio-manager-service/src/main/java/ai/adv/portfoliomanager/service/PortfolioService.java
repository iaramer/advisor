package ai.adv.portfoliomanager.service;

import ai.adv.portfoliomanager.dto.ModelPortfolioDto;
import ai.adv.portfoliomanager.dto.PortfolioDto;
import ai.adv.portfoliomanager.repository.PortfolioRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioService {

  private final PortfolioRepository portfolioRepository;
  private final PortfolioManagementService portfolioManagementService;

  public void create(PortfolioDto portfolioDto) {
    portfolioRepository.save(portfolioDto);
  }

  public List<PortfolioDto> getAllPortfolios() {
    return portfolioRepository.getAllPortfolios();
  }

  public Map<String, BigDecimal> formPortfolio(ModelPortfolioDto modelPortfolioDto) {
    return portfolioManagementService.formPortfolio(modelPortfolioDto);
  }
}
