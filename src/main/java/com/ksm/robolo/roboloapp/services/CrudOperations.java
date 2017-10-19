package com.ksm.robolo.roboloapp.services;

import java.util.UUID;

public interface CrudOperations<T> {



    T addItem(T item);

    Iterable<T> addItems(Iterable<T> items);

    // TODO remember to change to UUID when we migrate
    // UUID -  immutable universally unique identifier (UUID). no idea how it works by  I assume is good :D
    T getItemById(long id);
    
    // TODO remember to change to UUID when we migrate
    Iterable<T> getItemsByIds(Iterable<Long> ids);

    Iterable<T> getAll();

    void removeItem(T item);

    void removeItems(Iterable<T> items);
}
