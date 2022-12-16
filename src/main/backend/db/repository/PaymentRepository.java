package main.backend.db.repository;

import main.backend.db.Database;
import main.backend.model.booking.Payment;
import main.backend.utils.DbStringUtils;
import main.utils.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentRepository extends BaseRepository<Integer, Payment> {
    public PaymentRepository(String fileName, Database database) {
        super(fileName, database);
    }

    @Override
    public String recordToString(Payment record) {
        return DbStringUtils.defaultDbJoin(record.getPaymentId().toString(), record.getBookingId().toString(),
                DateUtils.dateToString(record.getPaymentDate()), record.getAmount().toString());
    }

    @Override
    public Payment stringToRecord(String str) {
        String[] strings = DbStringUtils.defaultDbSplit(str);
        return Payment.of(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]),
                DateUtils.stringToDate(strings[2]), new BigDecimal(strings[3]));
    }

    /* filter payment by date*/
    public List<Payment> getPaymentsByDate(LocalDate selectedDate) {
        return getLines()
                .filter(payment -> payment.getPaymentDate().equals(selectedDate))
                .collect(Collectors.toList());
    }

    /* filter payment between start date & end date*/
    public List<Payment> getPaymentBetween(LocalDate startDate, LocalDate endDate) {
        return getLines()
                .filter(payment -> DateUtils.isBetween(payment.getPaymentDate(), startDate, endDate))
                .collect(Collectors.toList());
    }
}
