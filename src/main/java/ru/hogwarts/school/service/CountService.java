package ru.hogwarts.school.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

import java.util.stream.IntStream;

@Service
public class CountService {
    private static final Logger logger = LoggerFactory.getLogger(CountService.class);

    public int sum() {
        int sum = IntStream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, Integer::sum);
        return sum;
//        Метод быстрее изначального примерно в полтора раза, возможно, можно сделать лучше
    }
}
