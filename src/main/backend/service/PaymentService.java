package main.backend.service;

import main.backend.model.booking.Payment;

import java.time.LocalDate;
import java.util.List;

public interface PaymentService {
    void makePayment(Payment initialize);

    /**
     * @return list of payments
     */
    List<Payment> getPaymentsByDate(LocalDate selectedDate);

    List<Payment> getAllPaymentsForMonth(LocalDate today);
}
