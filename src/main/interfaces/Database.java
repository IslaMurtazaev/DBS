package main.interfaces;

public interface Database {
    void save(Object object);

    Object retrive(int id);

    void delete(int id);

    Object update(Object object);
}
