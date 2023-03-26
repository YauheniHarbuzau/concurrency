package ru.clevertec.concurrency.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test for the {@link Client}
 */
public class ClientTest {

    @Nested
    class GetRequestListTest {
        @Test
        void checkGetRequestListShouldReturnCorrectListSize() {
            var list = new ArrayList<>(IntStream.rangeClosed(1, 100).boxed().toList());
            var client = new Client(list, 1);
            assertEquals(100, client.getRequestList().size());
        }

        @Test
        void checkGetRequestListShouldReturnCorrectListValues() {
            var list = new ArrayList<>(IntStream.rangeClosed(1, 20).boxed().toList());
            var client = new Client(list, 1);
            var requestList = client.getRequestList();
            assertAll(
                    () -> assertEquals(1, requestList.get(0).getValue()),
                    () -> assertEquals(10, requestList.get(9).getValue()),
                    () -> assertEquals(20, requestList.get(19).getValue())
            );
        }
    }

    @Nested
    class AccumulateTest {
        @Test
        void checkAccumulateShouldReturnCorrectResult5050() {
            var list = new ArrayList<>(IntStream.rangeClosed(1, 100).boxed().toList());
            var client = new Client(list, 1);
            var result = client.accumulate().get();
            assertAll(
                    () -> assertEquals(5050, result),
                    () -> assertNotEquals(0, result),
                    () -> assertNotEquals(null, result)
            );
        }

        @Test
        void checkAccumulateShouldReturnCorrectResult0() {
            var client = new Client(emptyList(), 1);
            assertEquals(0, client.accumulate().get());
        }
    }
}
