package ru.razilovcode.spboot.configs;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.razilovcode.spboot.logic.MessageSourceExtended;
import ru.razilovcode.spboot.models.User;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaProducerConfig {

    @Value("${spboot.kafka.bootstrapAddress}")
    private String kafkaBootstrapAddress;

    private final MessageSourceExtended messagesSource;

    @Autowired
    public KafkaProducerConfig(MessageSourceExtended messagesSource) {
        this.messagesSource = messagesSource;
    }

    @Bean
    public ProducerFactory<String, User> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaBootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, User> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ListenableFutureCallback<SendResult<String, User>> listenableFutureCallback() {
        return new ListenableFutureCallback<SendResult<String, User>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.error(messagesSource.formatMessage("error.kafka.send"), throwable);
            }

            @Override
            public void onSuccess(SendResult<String, User> stringStringSendResult) {
                log.info("{} : {}",
                        messagesSource.formatMessage("success.kafka.send"),
                        stringStringSendResult.getProducerRecord().value());
            }
        };
    }
}
