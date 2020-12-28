package ai.adv.data.kafka;

import ai.adv.data.dto.StockPriceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockPriceProducerService {

  @Value("${kafka.queues.stock-prices}")
  private String topic;

  private final KafkaTemplate<Object, StockPriceDto> kafkaTemplate;

  public void publish(StockPriceDto stockPriceDto) {
    kafkaTemplate.send(topic, stockPriceDto);
  }
}
