package com.epam.facultative.daos;

import java.util.List;

public interface Dao<T> {
    List<T> getAll();

    T getById(int id);

    T getByName(String name);

    void add(T t);

    void update(T t);

    void delete(int id);
}

