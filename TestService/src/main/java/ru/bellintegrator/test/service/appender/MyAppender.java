package ru.bellintegrator.test.service.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;


@Slf4j
public class MyAppender extends AppenderBase<ILoggingEvent> {
    @Setter
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    protected void append(ILoggingEvent event) {
        kafkaTemplate.send("log-topic", event.getFormattedMessage());
    }

}
