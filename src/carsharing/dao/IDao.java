package carsharing.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface IDao<T> {
    Optional<T> getById(int id);

    List<T> getAll();

    void save(T t);

    void update(T t, String[] params);

    void deleteById(Integer id);

    void deleteByName(String name);
}
