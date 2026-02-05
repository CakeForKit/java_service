package com.example.TestService.messaging;

import com.example.TestService.dto.MessageDto;

public interface MessagingService {
    public void send(MessageDto message);
}
