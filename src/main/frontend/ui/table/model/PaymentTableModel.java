package main.frontend.ui.table.model;

import main.backend.model.booking.Payment;

public class PaymentTableModel extends ListTableModel<Payment> {
    /** return table of payment details*/
    private static final String[] COLUMNS = {"Payment Id", "Booking Id", "Payment Date", "Total Price"};
    private static final ModelObjectAdapter<Payment> ADAPTER = (obj, idx) -> {
        switch (idx) {
            case 0:
                return obj.getPaymentId();
            case 1:
                return obj.getBookingId();
            case 2:
                return obj.getPaymentDate();
            case 3:
                return obj.getAmount();
            default:
                throw new IllegalArgumentException("Exceed column size");
        }
    };

    public PaymentTableModel() {
        super(COLUMNS, ADAPTER);
    }
}
