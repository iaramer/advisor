package ai.adv.financialdata.api;

import ai.adv.financialdata.dto.StockPriceDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class FacadeDataApi implements DataAPI {

  private final MoexDataAPI moexDataAPI;

  @Override
  public List<StockPriceDto> getStockPrices() {
    return moexDataAPI.getStockPrices();
  }
}
