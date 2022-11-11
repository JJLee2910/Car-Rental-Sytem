package main.backend.model.booking;

import main.backend.db.repository.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Booking implements Model<Integer> {
    private Integer bookingId;
    private String customer;
    private String carPlate;
    private BookingStatus status;
    private LocalDate created;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalPrice;

    private Booking(Integer bookingId, String customer, String carPlate,
                    BookingStatus status, LocalDate created, LocalDate startDate, LocalDate endDate, BigDecimal totalPrice) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.carPlate = carPlate;
        this.status = status;
        this.created = created;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
    }

    @Override
    public Integer getPrimaryKey() {
        return bookingId;
    }

    @Override
    public void setPrimaryKey(Integer pk) {
        bookingId = pk;
    }

    public static Booking initializeBooking(String customer, String carPlate, LocalDate startDate, LocalDate endDate, BigDecimal totalPrice) {
        return new Booking(null, customer, carPlate, BookingStatus.PENDING_PAYMENT, LocalDate.now(), startDate, endDate, totalPrice);
    }

    public static Booking of(int bookingId, String customer, String carPlate,
                             BookingStatus bookingStatus, LocalDate created, LocalDate startDate, LocalDate endDate, BigDecimal totalPrice) {
        return new Booking(bookingId, customer, carPlate, bookingStatus, created, startDate, endDate, totalPrice);
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public String getCustomer() {
        return customer;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public LocalDate getCreated() {
        return created;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
