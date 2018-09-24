package main.interfaces;

public interface Database {
    void save(Object object);

    void retrieve(long id, Object object );

    void delete(long id,Object object);

    void update(long id, Object object);
}
