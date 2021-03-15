package ai.adv.portfoliomanager.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * General interface for requesting data
 */
public interface DataService {

  Map<String, BigDecimal> getPricesByTickers(List<String> tickers);
}
