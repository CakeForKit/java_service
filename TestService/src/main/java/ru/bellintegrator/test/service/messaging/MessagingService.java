package ru.bellintegrator.test.service.messaging;

import ru.bellintegrator.test.service.dto.MessageDto;

public interface MessagingService {
    public void send(MessageDto message);
}
