package ru.bellintegrator.test.service.config;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TopicConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public TopicConfiguration() {
        System.out.println("CONSTRUCTOR: TopicConfiguration is being instantiated!");
    }
    @PostConstruct
    public void init() {
        System.out.println(" POSTCONSTRUCT: TopicConfiguration is initialized!");
        System.out.println("🔥 CHECKING EXISTING BEANS: 🔥");
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanNames) {
            if (name.contains("kafka") || name.contains("topic") || name.contains("admin")) {
                System.out.println("  FOUND BEAN: " + name);
            }
        }
    }

    @Bean
    public KafkaAdmin admin() {
        System.out.println("========== admin ============\n\n");
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 3000);
        System.out.println("========== out admin ============\n\n");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        System.out.println("========== create user topic ============\n\n");
        return TopicBuilder.name("user-topic").build();
    }

    @Bean
    public NewTopic topic2() {
        System.out.println("========== create log topic ============\n\n");
        return TopicBuilder.name("log-topic").build();
    }
}