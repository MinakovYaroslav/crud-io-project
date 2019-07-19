package com.minakov.crudioproject.repository;

import java.util.List;

public interface GenericRepository<T, ID> {

    List<T> findAll();

    void delete(ID id) throws Throwable;

    T findById(ID id) throws Throwable;

    T save(T t) throws Throwable;
}
