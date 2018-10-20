package main.interfaces;

public interface Database {

    Object save(Savable object);

    Object get(long id, Class aClass);

    void delete(long id, Class aClass);

    void update(long id, Savable object);
}
