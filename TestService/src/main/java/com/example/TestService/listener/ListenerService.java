package ru.bellintegrator.TestService.listener;

import ru.bellintegrator.TestService.dto.ActionUser;
import ru.bellintegrator.TestService.dto.MessageDto;
import ru.bellintegrator.TestService.services.ComsumerUserService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ListenerService {
    private final ComsumerUserService consumerUserServ;

    @PostConstruct
    public void init() {
        System.out.println("ListenerService initialized!");
    }

    @KafkaListener(topics="my.orders.topic", groupId = "testservice-group")
    public void handle(MessageDto message) {
        System.out.println(message);
        switch (message.getAction()) {
            case ActionUser.CREATE, ActionUser.UPDATE:
                consumerUserServ.save(message.getUser());
                break;
            case ActionUser.DELETE_BY_ID:
                consumerUserServ.deleteById(message.getUser().getId());
        }

    }
}
