package com.becomejavasenior;

import java.util.List;

public interface GenericDao<T>{
    T create(T object) throws DataBaseException;

    T read(int key) throws DataBaseException;

    T readLite(int key) throws DataBaseException;

    void update(T object) throws DataBaseException;

    void delete(int id) throws DataBaseException;

    List<T> readAll() throws DataBaseException;

    List<T> readAllLite() throws DataBaseException;
}