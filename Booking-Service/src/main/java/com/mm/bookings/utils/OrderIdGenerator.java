package com.mm.bookings.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class OrderIdGenerator {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd");

    private final AtomicInteger counter = new AtomicInteger(0);
    private final Random random = new Random();

    public String generate() {

        // 1. Date part
        String date = LocalDate.now().format(DATE_FORMAT);

        // 2. Sequence counter
        int sequence = counter.incrementAndGet(); // auto-increases 1,2,3...

        // 3. Random 2 uppercase letters
        String randomLetters = randomAlphabet(2);

        // 4. Build order ID
        return String.format("ORD-%s-%06d-%s", date, sequence, randomLetters);
        // Example: ORD-20250202-000001-AB
    }

    private String randomAlphabet(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char letter = (char) ('A' + random.nextInt(26)); // A–Z
            sb.append(letter);
        }
        return sb.toString();
    }
}
