package ru.clevertec.concurrency;

import org.junit.jupiter.api.Test;
import ru.clevertec.concurrency.service.Client;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration test
 */
public class MainTest {

    @Test
    void checkClientAccumulateShouldReturnCorrectResult() {
        var list1 = new ArrayList<>(IntStream.rangeClosed(1, 100).boxed().toList());
        var list2 = new ArrayList<>(IntStream.rangeClosed(1, 1000).boxed().toList());

        var result1 = new Client(list1, 1).accumulate().get();
        var result2 = new Client(list1, 1).accumulate().get();
        var result3 = new Client(list2, 1).accumulate().get();

        assertAll(
                () -> assertEquals(5050, result1),
                () -> assertEquals(0, result2),
                () -> assertEquals(500500, result3)
        );
    }
}
