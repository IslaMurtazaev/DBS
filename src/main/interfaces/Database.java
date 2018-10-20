package main.interfaces;

public interface Database {

    Object save(Savable object);

    Object get(long id, Savable object);

    void delete(long id, Savable object);

    void update(long id, Savable object);
}
