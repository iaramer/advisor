package ai.adv.data.utils.xml;

import ai.adv.data.dto.StockPriceDto;
import java.util.List;

public interface XmlProcessor {

  List<StockPriceDto> process(String xmlBody);
}
