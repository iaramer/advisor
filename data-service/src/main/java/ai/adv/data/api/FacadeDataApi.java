package ai.adv.data.api;

import ai.adv.data.dto.StockPriceDto;
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
