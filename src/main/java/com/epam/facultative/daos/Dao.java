package com.epam.facultative.daos;

import java.util.List;

public interface Dao<T> {
    List<T> findAll();

    T findById(int id);

    T findByName(String name);

    void add(T t);

    void update(T t);

    void delete(T t);
}
