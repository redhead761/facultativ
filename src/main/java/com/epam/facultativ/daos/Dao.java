package com.epam.facultativ.daos;

import java.util.List;

public interface Dao<T> {
    List<T> findAll();

    T findById(int id);

    void update(T t);

    void insert(T t);

    void delete(T t);
}
