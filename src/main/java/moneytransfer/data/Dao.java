package moneytransfer.data;

import moneytransfer.model.User;

import java.util.List;
import java.util.UUID;

public interface Dao<T> {

    T get(UUID id); // should really use Optional<T>

    List<T> getAll();

    T add(T t);

    T update(T t);

    void delete(T t);
}
