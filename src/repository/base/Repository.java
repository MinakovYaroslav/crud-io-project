package repository.base;

import exc.CustomerNotFoundException;
import exc.ProjectNotFoundException;

import java.util.Set;

public interface Repository<T, ID> {

    Set<T> findAll() throws Throwable;

    T findById(ID id) throws Throwable;

    T save(T t);
}
