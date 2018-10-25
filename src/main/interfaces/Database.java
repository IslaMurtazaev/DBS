package main.interfaces;

import java.util.List;

public interface Database {

    Object save(Savable object);

    Object get(long id, Class aClass);

    void delete(long id, Class aClass);

    void update(long id, Savable object);

    List findAll(Class aClass);
}
