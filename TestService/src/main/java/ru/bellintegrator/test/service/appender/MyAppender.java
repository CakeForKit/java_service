package ru.bellintegrator.test.service.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;


public class MyAppender extends AppenderBase<ILoggingEvent> {
    @Setter
    private KafkaTemplate<String, String> kafkaTemplate;
    @Getter @Setter
    private String prefix;

    @Override
    protected void append(ILoggingEvent event) {
        System.out.println("========== MyAppender append ============");
        var future = kafkaTemplate.send("log-topic", event.getFormattedMessage());
        System.out.println("========== out MyAppender append ============");
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                // УСПЕШНО - отправлено!
                RecordMetadata metadata = result.getRecordMetadata();
                System.out.println("✓ Сообщение успешно отправлено в Kafka:");
                System.out.println("  Топик: " + metadata.topic());
                System.out.println("  Партиция: " + metadata.partition());
                System.out.println("  Offset: " + metadata.offset());
                System.out.println("  Timestamp: " + metadata.timestamp());
                System.out.println("  Сообщение: " + event.getFormattedMessage());
            } else {
                // ОШИБКА
                System.err.println("✗ Ошибка отправки в Kafka: " + ex.getMessage());
            }
        });
    }

}
