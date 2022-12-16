package main.backend.db.repository;

import main.backend.db.Database;
import main.backend.model.booking.Booking;
import main.backend.model.booking.BookingStatus;
import main.backend.utils.DbStringUtils;
import main.utils.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class    BookingRepository extends BaseRepository<Integer, Booking> {
    public BookingRepository(String fileName, Database database) {
        super(fileName, database);
    }

    /* map from object to text*/
    @Override
    public String recordToString(Booking record) {
        return DbStringUtils.defaultDbJoin(record.getPrimaryKey().toString(), record.getCustomer(), record.getCarPlate(),
                record.getStatus().name(), DateUtils.dateToString(record.getCreated()),
                DateUtils.dateToString(record.getStartDate()), DateUtils.dateToString(record.getEndDate()), record.getTotalPrice().toString());
    }

    /* converting text to object*/
    @Override
    public Booking stringToRecord(String str) {
        String[] strings = DbStringUtils.defaultDbSplit(str);
        return Booking.of(Integer.parseInt(strings[0]), strings[1], strings[2], BookingStatus.valueOf(strings[3]),
                DateUtils.stringToDate(strings[4]), DateUtils.stringToDate(strings[5]),
                DateUtils.stringToDate(strings[6]), new BigDecimal(strings[7]));
    }


    /** get booking by filtering with start date and end date
     */
    public List<Booking> getBookingBetween(LocalDate startDate, LocalDate endDate) {
        return getLines()
                .filter(booking -> DateUtils.isOverlapped(booking.getStartDate(), booking.getEndDate(), startDate, endDate))
                .collect(Collectors.toList());
    }
}
