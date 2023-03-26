package ru.clevertec.concurrency.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static java.util.regex.Pattern.compile;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for the {@link Server}
 */
public class ServerTest {

    @Nested
    class ProcessingRequestTest {
        @Test
        void checkProcessingRequestShouldReturnCorrectResponse() {
            var list = new ArrayList<>(IntStream.rangeClosed(1, 10).boxed().toList());
            var client = new Client(list, 1);
            var fResponse = new Server(1).processingRequest(client.getRequestList());
            assertAll(
                    () -> assertEquals(10, fResponse.get().getListSize()),
                    () -> assertEquals("12345678910", fResponse.get().getListValues())
            );
        }
    }

    @Nested
    class ListValuesToStringTest {
        @Test
        void checkListValuesToStringShouldReturnCorrectResult() throws ExecutionException, InterruptedException {
            var list = new ArrayList<>(IntStream.rangeClosed(1, 20).boxed().toList());
            var client = new Client(list, 1);
            var fResponse = new Server(1).processingRequest(client.getRequestList());
            assertEquals("1234567891011121314151617181920", fResponse.get().getListValues());
        }

        @Test
        void checkListValuesToStringShouldReturnValidResult() throws ExecutionException, InterruptedException {
            var list = new ArrayList<>(IntStream.rangeClosed(1, 20).boxed().toList());
            var client = new Client(list, 1);
            var fResponse = new Server(1).processingRequest(client.getRequestList());
            var listValues = fResponse.get().getListValues();
            assertTrue(compile("^\\d+$").matcher(listValues).matches());
        }
    }
}
