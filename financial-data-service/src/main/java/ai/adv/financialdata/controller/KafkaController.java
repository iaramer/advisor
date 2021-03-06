package ai.adv.financialdata.controller;

import ai.adv.financialdata.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
@RequiredArgsConstructor
public class KafkaController {

  private final KafkaProducerService kafkaProducerService;

  @PostMapping(value = "/publish")
  public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
    kafkaProducerService.sendMessage(message);
  }
}
