package ru.clevertec.concurrency;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        new Client(new ArrayList<>(IntStream.rangeClosed(1, 100).boxed().toList()), 2).accumulate();
    }
}
