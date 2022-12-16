package main.backend.service;

import main.backend.model.booking.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    void makeBooking(Booking booking);
    /** @return all bookings within the start date & end date
     */
    List<Booking> getBookingsWithinDates(LocalDate startDate, LocalDate endDate);

    /**
     * @return all booking list
     */
    List<Booking> getAllBookings();

    /**
     * @return all booking list by filter customers name
     */
    List<Booking> getAllBookingsByCustomer(String customerName);

    void paymentDone(Integer bookingId);

    List<Booking> getAllBookingsForMonth(LocalDate day);

    Booking getById(Integer bookingId);
}
