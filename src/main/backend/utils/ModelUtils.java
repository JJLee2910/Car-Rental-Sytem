package main.backend.utils;

import main.backend.db.repository.Model;
import main.backend.db.repository.Repository;

public class ModelUtils {
    /** generate primary key
     */
    public static <T extends Model<Integer>>void generateSerialIfNew(T model, Repository<Integer, T> repository) {
        if (model.getPrimaryKey() == null)
            model.setPrimaryKey((int) repository.getLines().count() + 1);
    }
}
