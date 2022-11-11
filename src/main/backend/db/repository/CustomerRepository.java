package main.backend.db.repository;

import main.backend.db.Database;
import main.backend.model.user.Customer;
import main.backend.model.user.Gender;
import main.backend.utils.DbStringUtils;

import java.util.Optional;

public class CustomerRepository extends BaseRepository<String, Customer> implements LoginRepository<Customer> {

    public CustomerRepository(String fileName, Database database) {
        super(fileName, database);
    }

    @Override
    public String recordToString(Customer record) {
        return DbStringUtils.defaultDbJoin(record.getUsername(), record.getPassword(), record.getGender().name(),
                record.getAge().toString(), record.getPhone(), record.getEmail(), record.getAddress());
    }

    @Override
    public Customer stringToRecord(String str) {
        String[] strings = DbStringUtils.defaultDbSplit(str);
        return new Customer(strings[0], strings[1], Gender.valueOf(strings[2]), Integer.parseInt(strings[3]),
                strings[4], strings[5], strings[6]);
    }

    /*filter customer by username*/
    @Override
    public Optional<Customer> findByUsername(String username) {
        return getLines().filter(customer -> customer.getUsername().equals(username)).findFirst();
    }
}
