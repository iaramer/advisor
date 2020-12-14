package ai.adv.portfoliomanager.controller;

import ai.adv.portfoliomanager.dto.PortfolioDto;
import ai.adv.portfoliomanager.service.PortfolioService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/portfolios")
public class PortfolioController {

  private final PortfolioService portfolioService;

  @GetMapping("/all")
  public List<PortfolioDto> getAllPortfolios() {
    return portfolioService.getAllPortfolios();
  }

  @PostMapping("/form")
  public Map<String, BigDecimal> formPortfolio(PortfolioDto portfolioDto) {
    return portfolioService.formPortfolio();
  }
}
