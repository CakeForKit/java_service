package ru.bellintegrator.test.service.listener;

import ru.bellintegrator.test.service.dto.ActionUser;
import ru.bellintegrator.test.service.dto.MessageDto;
import ru.bellintegrator.test.service.services.ComsumerUserService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
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
