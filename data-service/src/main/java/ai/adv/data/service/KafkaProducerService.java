package ai.adv.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {

  private static final String TOPIC = "stocks";

  private final KafkaTemplate<String, String> kafkaTemplate;

  public void sendMessage(String message) {
    log.info(String.format("#### -> Producing message -> %s", message));
    kafkaTemplate.send(TOPIC, message);
  }
}
