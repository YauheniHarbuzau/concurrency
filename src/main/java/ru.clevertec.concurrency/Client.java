package ru.clevertec.concurrency;

import ru.clevertec.concurrency.entity.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Collections.emptyList;

/**
 * Клиент
 */
public class Client {

    private final List<Integer> intList;
    private final Server server;

    public Client(List<Integer> intList, int nThreads) {
        this.intList = intList;
        this.server = new Server(nThreads);
    }

    /**
     * Получение List<Request> из List<Integer> для дальнейшей обработки на Server,
     * при этом значения удаляются из List<Integer>
     */
    public List<Request> getRequestList() {
        if (intList != null) {
            System.out.println("List<Integer> size before remove: " + intList.size());

            List<Request> requestList = new ArrayList<>();
            for (int i = 0; i < intList.size(); ) {
                var value = intList.get(i);
                requestList.add(new Request(value));
                intList.remove(value);
            }

            System.out.println("List<Integer> size after remove: " + intList.size());
            return requestList;
        } else {
            return emptyList();
        }
    }

    /**
     * Аккумулирование полученного от Server ответа Future<Response>
     */
    public AtomicReference<Integer> accumulate() {
        try {
            var listSize = server.processingRequest(getRequestList()).get().getListSize();

            AtomicReference<Integer> accumulator = new AtomicReference<>(0);
            accumulator.updateAndGet(i -> (listSize + 1) * listSize / 2);
            System.out.println("Accumulator: " + accumulator);

            return accumulator;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
