package main.backend.db.repository;

import main.backend.db.Database;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class BaseRepository<K, T extends Model<K>> implements Repository<K, T> {
    private final String fileName;
    protected final Database database;
    private final LinkedHashMap<K, T> inMemoryMap = new LinkedHashMap<>();

    protected BaseRepository(String fileName, Database database) {
        this.fileName = fileName;
        this.database = database;
        database.register(this);
        loadToMemory();
    }

    /* load text file in memory
    * called during project startup
    * */
    private void loadToMemory() {
        database.getLines(this)
                .map(this::stringToRecord)
                .forEach(t -> inMemoryMap.put(t.getPrimaryKey(), t));
    }

    @Override
    public String getFileName() {
        return fileName;
    }


    /* inserting record data*/
    public void insert(T record) {
        inMemoryMap.put(record.getPrimaryKey(), record);
        database.appendAtEnd(this, recordToString(record));
    }

    /* update the record data*/
    public void update(T record) {
        inMemoryMap.replace(record.getPrimaryKey(), record);
        database.rewriteFile(this);
    }

    /* remove the recrod data*/
    public void remove(T record) {
        inMemoryMap.remove(record.getPrimaryKey());
        database.rewriteFile(this);
    }

    public Stream<T> getLines() {
        return inMemoryMap.values().stream();
    }

    public Optional<T> findByPrimaryKey(K key) {
        return Optional.ofNullable(getByPrimaryKey(key));
    }

    @Override
    public T getByPrimaryKey(K key) {
        return inMemoryMap.get(key);
    }
}
