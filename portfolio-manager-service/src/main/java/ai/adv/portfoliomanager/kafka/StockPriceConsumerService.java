package ai.adv.portfoliomanager.kafka;

import ai.adv.financialdata.dto.StockPriceDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockPriceConsumerService {

  private static final String TOPIC = "stock-prices";

  @KafkaListener(topics = TOPIC, groupId = "group_id")
  public void consume(String message) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      StockPriceDto stockPriceDto = objectMapper.readValue(message, StockPriceDto.class);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
    }
  }
}
