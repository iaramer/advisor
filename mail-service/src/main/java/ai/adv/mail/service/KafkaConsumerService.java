package ai.adv.mail.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    private static final String TOPIC = "stocks";

    @KafkaListener(topics = TOPIC, groupId = "group_id")
    public void consume(String message) {
        log.info(String.format("#### -> Consumed message (mail service) -> %s", message));
    }
}
