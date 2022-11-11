package main.frontend.ui;

import main.Application;
import main.backend.model.booking.Booking;
import main.backend.model.booking.BookingStatus;
import main.backend.service.BookingService;
import main.frontend.ui.table.model.CustomerBookingTableModel;
import main.frontend.util.SwingUtils;

import javax.swing.*;

public class CustomerBookingHistoryPage extends BasePage {
    private JPanel mainPanel;
    private JTable bookingTable;
    private JButton openReceiptButton;
    private JButton backButton;

    private CustomerBookingTableModel bookingTableModel;

    private final BookingService bookingService;


    public CustomerBookingHistoryPage(Application application, BookingService bookingService) {
        super(application);
        this.bookingService = bookingService;

        backButton.addActionListener(this::backToPreviousPage);
        bookingTable.getSelectionModel().addListSelectionListener(e -> onItemSelected());
        openReceiptButton.addActionListener(e -> openReceipt());
    }

    private void openReceipt() {
        int selectedRow = bookingTable.getSelectedRow();
        Booking booking = bookingTableModel.getModelAt(selectedRow);

        if (booking.getStatus().equals(BookingStatus.PENDING_PAYMENT)) {
            SwingUtils.promptMessageError("Booking has not been paid yet!");
            return;
        }

        application.toPaymentDetailPage(booking);
    }

    private void onItemSelected() {
        int[] selectedRows = bookingTable.getSelectedRows();
        if (selectedRows.length == 1) {
            openReceiptButton.setEnabled(true);
            return;
        }

        openReceiptButton.setEnabled(false);
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        bookingTableModel = new CustomerBookingTableModel();
        bookingTable = new JTable(bookingTableModel);
    }

    @Override
    public void refreshData() {
        bookingTable.clearSelection();
        bookingTableModel.refreshData(bookingService.getAllBookingsByCustomer(getCurrentUserName()));

        onItemSelected();
    }
}
