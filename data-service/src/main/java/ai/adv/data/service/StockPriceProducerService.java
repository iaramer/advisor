package ai.adv.data.service;

import ai.adv.data.api.DataAPI;
import ai.adv.data.dto.StockPriceDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class StockPriceProducerService {

  @Value("${kafka.queues.stock-prices}")
  private String topic;

  private final KafkaTemplate<String, StockPriceDto> kafkaTemplate;

  @Scheduled(cron = "${cron.refresh.stock-prices}")
  public void publishStockPrices() {
    log.info("Test publishing");
    StockPriceDto stockPriceDto = new StockPriceDto("MAMO", BigDecimal.TEN, 2, 1);
    publish(stockPriceDto);
  }

  public void publish(StockPriceDto stockPriceDto) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      Message<String> message = MessageBuilder
          .withPayload(mapper.writeValueAsString(stockPriceDto))
          .setHeader(KafkaHeaders.TOPIC, topic)
          .build();
      kafkaTemplate.send(message);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
