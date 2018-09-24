package main.interfaces;

public interface Database {
    void save(Object object);

    Object retrive(long id);

    void delete(long id,Object object);

    void update(long id, Object object);
}
