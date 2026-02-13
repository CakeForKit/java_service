package ru.bellintegrator.test.service.listener;

import ru.bellintegrator.test.service.dto.ActionUser;
import ru.bellintegrator.test.service.dto.UserEventDto;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.bellintegrator.test.service.service.UserService;

@Component
@AllArgsConstructor
public class UserListener {
    private final UserService service;

    @KafkaListener(topics="user-topic", groupId = "testservice-group",
            containerFactory = "userEventKafkaListenerContainerFactory")
    public void handle(UserEventDto message) {
        switch (message.getAction()) {
            case ActionUser.CREATE, ActionUser.UPDATE:
                service.upsertForMB(message.getUser());
                break;
            case ActionUser.DELETE_BY_ID:
                service.deleteByIdForMB(message.getUser().getId());
                break;
        }
    }
}
