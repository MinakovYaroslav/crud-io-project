package repository;

import exc.CustomerNotFoundException;
import exc.ProjectNotFoundException;

import java.util.List;
import java.util.Set;

public interface GenericRepository<T, ID> {

    List<T> findAll() throws Throwable;

    void delete(ID id) throws Throwable;

    T findById(ID id) throws Throwable;

    T save(T t);
}
