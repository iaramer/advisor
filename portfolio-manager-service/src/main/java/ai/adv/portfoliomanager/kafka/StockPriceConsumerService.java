package ai.adv.portfoliomanager.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockPriceConsumerService {

  @KafkaListener(topics = "#{'${kafka.queues.stock-prices}'}")
  public void consume(String message) { // todo: argument StockPriceDto
    log.info("Got it!");
  }
}
