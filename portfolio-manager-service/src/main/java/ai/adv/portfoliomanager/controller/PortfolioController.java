package ai.adv.portfoliomanager.controller;

import ai.adv.portfoliomanager.dto.PortfolioDto;
import ai.adv.portfoliomanager.service.PortfolioService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
}
