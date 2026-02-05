package ru.bellintegrator.TestService.messaging;

import ru.bellintegrator.TestService.dto.MessageDto;

public interface MessagingService {
    public void send(MessageDto message);
}
