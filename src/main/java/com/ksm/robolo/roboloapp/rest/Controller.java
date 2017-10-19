package com.ksm.robolo.roboloapp.rest;

import java.util.UUID;

public interface Controller<T> {
    String addItem(T item);

    String addItems(Iterable<T> items);

    String getItemById(long id);

    String getItemsByIds(Iterable<Long> ids);

    void removeItem(T item);

    void removeItems(Iterable<T> items);
}