package ru.razilovcode.spboot.logic;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MessageSourceExtended extends ReloadableResourceBundleMessageSource
        implements MessageSource {

    public MessageSourceExtended() {
        setBasename("classpath:/errors");
        setDefaultEncoding(StandardCharsets.UTF_8.name());
    }

    @Override
    public String formatMessage(String msg, Object... args) {
        return String.format(super.getMessage(msg, null, null), args);
    }
}