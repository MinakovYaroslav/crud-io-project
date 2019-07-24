package com.minakov.crudioproject.repository;

import java.util.List;

public interface GenericRepository<T, ID> {

    List<T> findAll();

    void delete(ID id);

    T findById(ID id) throws Throwable;

    T create(T t);

    T update(T t);
}
