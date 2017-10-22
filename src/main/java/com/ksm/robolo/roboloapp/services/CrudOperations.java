package com.ksm.robolo.roboloapp.services;

import java.util.UUID;

public interface CrudOperations<T> {


    T addItem(T item);

    Iterable<T> addItems(Iterable<T> items);

    T getItemById(long id);

    Iterable<T> getItemsByIds(Iterable<Long> ids);

    Iterable<T> getAll();

    void removeItem(T item);

    void removeItems(Iterable<T> items);
}
