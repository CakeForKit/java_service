package ru.bellintegrator.test_service.messaging;

import ru.bellintegrator.test_service.dto.MessageDto;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaMessagingService implements MessagingService {
    private KafkaTemplate<String, MessageDto> kafkaTemplate;

    @Override
    public void send(MessageDto message) {
        kafkaTemplate.send("my.orders.topic", message);
    }
}