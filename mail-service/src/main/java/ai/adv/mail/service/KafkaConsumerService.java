package ai.adv.mail.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {

  private static final String TOPIC = "email-messages";

  private final MailService mailService;

  @KafkaListener(topics = TOPIC, groupId = "group_id")
  public void consume(String message) {
    mailService.sendEmailMessage(message);
  }
}
