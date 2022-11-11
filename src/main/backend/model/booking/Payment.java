package main.backend.model.booking;

import main.backend.db.repository.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment implements Model<Integer> {
    private Integer paymentId;
    private Integer bookingId;
    private LocalDate paymentDate;
    private BigDecimal amount;

    public Payment(Integer paymentId, Integer bookingId, LocalDate paymentDate, BigDecimal amount) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    public static Payment initialize(Integer bookingId, BigDecimal totalPrice) {
        return new Payment(null, bookingId, LocalDate.now(), totalPrice);
    }

    public static Payment of(int paymentId, int bookingId, LocalDate paymentDate, BigDecimal totalPrice) {
        return new Payment(paymentId, bookingId, paymentDate, totalPrice);
    }

    @Override
    public Integer getPrimaryKey() {
        return paymentId;
    }

    @Override
    public void setPrimaryKey(Integer pk) {
        paymentId = pk;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
