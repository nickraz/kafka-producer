package ru.razilovcode.spboot.configs;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.razilovcode.spboot.models.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Configuration
@EnableScheduling
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SheduleConfig {

    @Value("${spboot.kafka.topic}")
    @NonFinal
    String topic;

    Random random = new Random(System.currentTimeMillis());

    KafkaTemplate<String, User> kafkaTemplate;
    ListenableFutureCallback<SendResult<String, User>> listenableFutureCallback;

    @Autowired
    public SheduleConfig(
            KafkaTemplate<String, User> kafkaTemplate,
            ListenableFutureCallback<SendResult<String, User>> listenableFutureCallback) {
        this.kafkaTemplate = kafkaTemplate;
        this.listenableFutureCallback = listenableFutureCallback;
    }

    @Scheduled(fixedDelay = 5000)
    public void proc() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = simpleDateFormat.format(new Date());
        ListenableFuture<SendResult<String, User>> future = kafkaTemplate.send(topic, new User("Nikolay", "Razilov"));
        future.addCallback(listenableFutureCallback);
    }
}
