package main.backend.db.repository;

import java.util.Optional;
import java.util.stream.Stream;

public interface Repository<K, T extends Model<K>> {
    String getFileName();
    Stream<T> getLines();
    String recordToString(T record);
    T stringToRecord(String str);

    void insert(T record);
    void update(T record);
    void remove(T record);
    Optional<T> findByPrimaryKey(K key);
    T getByPrimaryKey(K key);
}
