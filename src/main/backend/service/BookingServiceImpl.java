package main.backend.service;

import main.backend.db.repository.BookingRepository;
import main.backend.model.booking.Booking;
import main.backend.model.booking.BookingStatus;
import main.backend.utils.ModelUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void makeBooking(Booking booking) {
        ModelUtils.generateSerialIfNew(booking, bookingRepository);
        bookingRepository.insert(booking);
    }

    @Override
    public List<Booking> getBookingsWithinDates(LocalDate startDate, LocalDate endDate) {
        return bookingRepository.getBookingBetween(startDate, endDate);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.getLines()
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> getAllBookingsByCustomer(String customerName) {
        return bookingRepository.getLines()
                .filter(booking -> booking.getCustomer().equals(customerName))
                .collect(Collectors.toList());
    }

    @Override
    public void paymentDone(Integer bookingId) {
        Booking booking = bookingRepository.getByPrimaryKey(bookingId);
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.update(booking);
    }

    @Override
    public List<Booking> getAllBookingsForMonth(LocalDate date) {
        return getBookingsWithinDates(date.withDayOfMonth(0), date.withDayOfMonth(date.getDayOfMonth()));
    }

    @Override
    public Booking getById(Integer bookingId) {
        return bookingRepository.getByPrimaryKey(bookingId);
    }
}
