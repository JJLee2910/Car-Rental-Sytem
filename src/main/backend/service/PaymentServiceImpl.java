package main.backend.service;

import main.backend.db.repository.PaymentRepository;
import main.backend.model.booking.Payment;
import main.backend.utils.ModelUtils;

import java.time.LocalDate;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void makePayment(Payment payment) {
        ModelUtils.generateSerialIfNew(payment, paymentRepository);
        paymentRepository.insert(payment);
    }

    @Override
    public List<Payment> getPaymentsByDate(LocalDate selectedDate) {
        return paymentRepository.getPaymentsByDate(selectedDate);
    }

    @Override
    public List<Payment> getAllPaymentsForMonth(LocalDate today) {
        if(today == null) return List.of();
        LocalDate start = today.withDayOfMonth(1);
        LocalDate end = today.plusMonths(1).withDayOfMonth(1).minusDays(1);
        return paymentRepository.getPaymentBetween(start, end);
    }
}
