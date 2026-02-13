package ru.bellintegrator.test.service.appender;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@AllArgsConstructor
@Qualifier("logKafkaTemplate")
public class LogbackKafkaConfig {

    private KafkaTemplate<String, String> kafkaTemplate;

    @PostConstruct
    public void init() {
        System.out.println("========== init LogbackKafkaConfig ============\n\n");
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
        appender.setPrefix("test");
        appender.start();

        // Добавляем к корневому логгеру
//        Logger rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME);
//        rootLogger.addAppender(appender);

        // Добавляем к логгеру вашего сервиса
        Logger serviceLogger = context.getLogger("ru.bellintegrator.test.service");
        serviceLogger.addAppender(appender);
        System.out.println("========== out init LogbackKafkaConfig ============\n\n");
    }
}