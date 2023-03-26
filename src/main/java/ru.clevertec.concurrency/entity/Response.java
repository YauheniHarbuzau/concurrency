package ru.clevertec.concurrency.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response {

    private int listSize;
    private String listValues;
}
