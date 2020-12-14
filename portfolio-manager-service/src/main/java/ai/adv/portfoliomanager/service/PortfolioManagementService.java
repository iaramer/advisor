package ai.adv.portfoliomanager.service;

import ai.adv.portfoliomanager.dto.PortfolioDto;
import balancing.Balancer;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioManagementService {

  public List<PortfolioDto> formPortfolio() {
    Balancer balancer = new Balancer("USD");
    return Collections.emptyList();
  }
}
