package ai.adv.data.kafka;

import ai.adv.data.dto.StockPriceDto;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
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

  private final KafkaTemplate<Object, StockPriceDto> kafkaTemplate;

  @Scheduled(cron = "${cron.refresh.stock-prices}")
  public void testPublish() {
    log.info("Test publishing");
    StockPriceDto stockPriceDto = new StockPriceDto("MAMO", BigDecimal.TEN);
    publish(stockPriceDto);
  }

  public void publish(StockPriceDto stockPriceDto) {
    kafkaTemplate.send(topic, stockPriceDto);
  }
}
