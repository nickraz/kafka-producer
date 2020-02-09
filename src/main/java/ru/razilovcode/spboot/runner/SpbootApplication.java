package ru.razilovcode.spboot.runner;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import ru.razilovcode.spboot.logic.MessageSourceExtended;

import javax.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan(basePackages = "ru.razilovcode.spboot")
@Slf4j
public class SpbootApplication {

    @Setter(onMethod = @__(@Autowired))
    private MessageSourceExtended messageSource;

    @PostConstruct
    private void postConstract() {
        Runtime.getRuntime().addShutdownHook(
                new Thread(() ->
                        log.info(messageSource.formatMessage("spboot.exit", "---===", "===---"))));
    }

    public static void main(String[] args) {
        SpringApplication.run(SpbootApplication.class, args);
    }

}