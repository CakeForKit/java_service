package ru.bellintegrator.test.service.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.bellintegrator.test.service.dto.UserEventDto;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean(name = "userEventProducerFactory")
    public ProducerFactory<String, UserEventDto> userEventProducerFactory() {
        System.out.println("========== userEventProducerFactory ============\n\n");
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        System.out.println("========== out userEventProducerFactory ============\n\n");
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean(name = "userEventKafkaTemplate")
    public KafkaTemplate<String, UserEventDto> userEventKafkaTemplate() {
        System.out.println("========== userEventKafkaTemplate ============\n\n");
        KafkaTemplate<String, UserEventDto> template = new KafkaTemplate<>(userEventProducerFactory());
        template.setDefaultTopic("user-topic");
        System.out.println("========== out userEventKafkaTemplate ============\n\n");
        return template;
    }

    @Bean(name = "logProducerFactory")
    public ProducerFactory<String, String> logProducerFactory() {
        System.out.println("========== logProducerFactory ============\n\n");
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        System.out.println("========== out logProducerFactory ============\n\n");
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean(name = "logKafkaTemplate")
    public KafkaTemplate<String, String> logKafkaTemplate() {
        System.out.println("========== logKafkaTemplate ============\n\n");
        KafkaTemplate<String, String> template = new KafkaTemplate<>(logProducerFactory());
        template.setDefaultTopic("log-topic");
        System.out.println("========== out logKafkaTemplate ============\n\n");
        return template;
    }
}