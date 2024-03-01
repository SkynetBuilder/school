package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.CountService;

@RestController
public class CountController {
    private final CountService countService;

    public CountController(CountService countService) {
        this.countService = countService;
    }

    @GetMapping("sum")
    public int sum() {
        return countService.sum();
    }
}
