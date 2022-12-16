package main.backend.db.repository;

public interface Model<T> {
    T getPrimaryKey();
    void setPrimaryKey(T pk);
}
