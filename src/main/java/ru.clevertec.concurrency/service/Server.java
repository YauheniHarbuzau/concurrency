package ru.clevertec.concurrency.service;

import ru.clevertec.concurrency.entity.Request;
import ru.clevertec.concurrency.entity.Response;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Сервер для работы с клиентом
 */
public class Server {

    private final Lock lock;
    private final ExecutorService executor;

    public Server(int nThreads) {
        this.lock = new ReentrantLock();
        this.executor = Executors.newFixedThreadPool(nThreads);
    }

    /**
     * Получение из List<Request> ответа Future<Response>, содержащего размерность List<Request>
     * и строку со значениями
     *
     * @see #listValuesToString(List<Request>)
     */
    public Future<Response> processingRequest(List<Request> requestList) {
        try {
            lock.lock();
            Thread.sleep((long) (100 + Math.random() * 900));

            var listSize = requestList.size();
            var listValues = listValuesToString(requestList);
            var response = new Response(listSize, listValues);
            var fResponse = executor.submit(() -> response);
            executor.shutdown();

            System.out.println("List<Request>: " + listValues);
            System.out.println("List<Request> size: " + listSize);

            return fResponse;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    /**
     * Получение из List<Request> строки значений (значения подряд без пробелов)
     */
    public String listValuesToString(List<Request> requestList) {
        lock.lock();
        StringBuilder result = new StringBuilder();
        for (Request request : requestList) {
            result.append(request.getValue());
        }
        lock.unlock();
        return result.toString();
    }
}
