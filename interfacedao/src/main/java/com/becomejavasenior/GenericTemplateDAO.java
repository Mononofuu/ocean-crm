package com.becomejavasenior;

import java.util.List;

/**
 * @author Lybachevskiy.Vladislav
 */

public interface GenericTemplateDAO<T> {

    void create(T object);

    T read(int key);

    void update(T object);

    void delete(int id);

    List<T> readAll();
}
