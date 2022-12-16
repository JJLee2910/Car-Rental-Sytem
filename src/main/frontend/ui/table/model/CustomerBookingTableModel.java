package main.frontend.ui.table.model;

import main.backend.model.booking.Booking;

public class CustomerBookingTableModel extends ListTableModel<Booking> {
    /** return table of customers booking*/
    private static final String[] COLUMNS = {"Booking Id", "Car", "Booking Date", "Start Date", "End Date", "Total Price", "Status"};
    private static final ModelObjectAdapter<Booking> ADAPTER = (obj, idx) -> {
        switch (idx) {
            case 0:
                return obj.getBookingId();
            case 1:
                return obj.getCarPlate();
            case 2:
                return obj.getCreated();
            case 3:
                return obj.getStartDate();
            case 4:
                return obj.getEndDate();
            case 5:
                return obj.getTotalPrice();
            case 6:
                return obj.getStatus().name();
            default:
                throw new IllegalArgumentException("Exceed column size");
        }
    };

    public CustomerBookingTableModel() {
        super(COLUMNS, ADAPTER);
    }
}
