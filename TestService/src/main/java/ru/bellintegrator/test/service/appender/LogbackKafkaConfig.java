package ru.bellintegrator.test.service.appender;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@AllArgsConstructor
@Qualifier("logKafkaTemplate")
public class LogbackKafkaConfig {

    private KafkaTemplate<String, String> kafkaTemplate;

    @PostConstruct
    public void init() {
        try {
            kafkaTemplate.partitionsFor("log-topic");
        } catch (Exception e) {
            System.err.println("Ошибка инициализации метаданных Kafka: " + e.getMessage());
        }

        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        MyAppender appender = new MyAppender();
        appender.setKafkaTemplate(kafkaTemplate);
        appender.setContext(context);
        appender.setName("MY_APPENDER");
        appender.start();

        Logger serviceLogger = context.getLogger("ru.bellintegrator.test.service");
        serviceLogger.addAppender(appender);
    }
}