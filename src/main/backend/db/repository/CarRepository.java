package main.backend.db.repository;

import main.backend.db.Database;
import main.backend.model.car.Car;
import main.backend.model.car.Transmission;
import main.backend.utils.DbStringUtils;

import java.math.BigDecimal;

public class CarRepository extends BaseRepository<String, Car> {
    public CarRepository(String fileName, Database database) {
        super(fileName, database);
    }

    /* mapping object to text*/
    @Override
    public String recordToString(Car record) {
        return DbStringUtils.defaultDbJoin(record.getCarPlate(), record.getBrand(), record.getModel(),
                String.valueOf(record.getSeats()), record.getPrice().toString(), record.getTransmission().name());
    }

    /* converting text to object*/
    @Override
    public Car stringToRecord(String str) {
        String[] strings = DbStringUtils.defaultDbSplit(str);
        return new Car(strings[0], strings[1], strings[2], Integer.parseInt(strings[3]),
                new BigDecimal(strings[4]), Transmission.valueOf(strings[5]));
    }
}
