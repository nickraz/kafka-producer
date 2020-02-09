package ru.razilovcode.spboot.logic;

public interface MessageSource {
    String formatMessage(String msg, Object[] args);
}
