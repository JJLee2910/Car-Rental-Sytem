package main.backend.db.repository;

import main.backend.db.Database;
import main.backend.model.user.Admin;
import main.backend.utils.DbStringUtils;

import java.util.Optional;

/* backend logic for admin database
* find all admin records by filtering username
* */
public class AdminRepository extends BaseRepository<String, Admin> implements LoginRepository<Admin> {
    public AdminRepository(String fileName, Database database) {
        super(fileName, database);
    }

    /* mapping from object to text*/
    @Override
    public String recordToString(Admin record) {
        return DbStringUtils.defaultDbJoin(record.getUsername(), record.getPassword());
    }

    /* convert from text to object*/
    @Override
    public Admin stringToRecord(String str) {
        String[] split = DbStringUtils.defaultDbSplit(str);
        return new Admin(split[0], split[1]);
    }

    public long getTotalRecord() {
        return database.getTotalCount(this);
    }

    @Override
    public Optional<Admin> findByUsername(String username) {
        return getLines()
                .filter(admin -> admin.getUsername().equals(username))
                .findFirst();
    }
}
