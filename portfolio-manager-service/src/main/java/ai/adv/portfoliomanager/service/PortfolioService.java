package ai.adv.portfoliomanager.service;

import ai.adv.portfoliomanager.dto.PortfolioDto;
import ai.adv.portfoliomanager.repository.PortfolioRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PortfolioService {

  private final PortfolioRepository portfolioRepository;

  public void create(PortfolioDto portfolioDto) {
    portfolioRepository.save(portfolioDto);
  }

  public List<PortfolioDto> getAllPortfolios() {
    return portfolioRepository.getAllPortfolios();
  }
}
