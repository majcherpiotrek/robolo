package com.ksm.robolo.roboloapp.services;

import org.springframework.data.repository.CrudRepository;

public abstract class AbstractService<T> implements CrudOperations<T> {
    protected CrudRepository<T, Long> repository;

    @Override
    public T addItem(T item) {
        return repository.save(item);
    }

    @Override
    public Iterable<T> getAll() {
        return repository.findAll();
    }

    @Override
    public Iterable<T> addItems(Iterable<T> items) {
        return repository.save(items);
    }
    

    @Override
    public T getItemById(long id) {
        return repository.findOne(id);
    }

    @Override
    public Iterable<T> getItemsByIds(Iterable<Long> ids) {
        return repository.findAll(ids);
    }

    @Override
    public void removeItem(T item) {
        repository.delete(item);
    }

    @Override
    public void removeItems(Iterable<T> items) {
        repository.delete(items);
    }
}
