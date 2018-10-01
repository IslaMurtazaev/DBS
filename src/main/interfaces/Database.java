package main.interfaces;

public interface Database {

    void save(Savable object);

    Object retrieve(long id, Savable object);

    void delete(long id, Savable object);

    void update(long id, Savable object);
}
