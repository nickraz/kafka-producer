package ru.razilovcode.spboot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.razilovcode.spboot.configs.SpbootURLs.MAIN_PAGE;

@RestController
@RequestMapping(value = MAIN_PAGE)
public class IndexController {

    private static final Map<String, String> map = Stream.of(
            new AbstractMap.SimpleEntry<>("login", "nick"),
            new AbstractMap.SimpleEntry<>("salt", "sdJds8sdh"))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    @GetMapping
    public Map<String, String> index() {
        return map;
    }
}
