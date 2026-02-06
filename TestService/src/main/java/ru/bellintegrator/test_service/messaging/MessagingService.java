package ru.bellintegrator.test_service.messaging;

import ru.bellintegrator.test_service.dto.MessageDto;

public interface MessagingService {
    public void send(MessageDto message);
}
