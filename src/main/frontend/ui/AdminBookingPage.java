package main.frontend.ui;

import main.Application;
import main.backend.model.booking.Booking;
import main.backend.model.booking.BookingStatus;
import main.backend.model.booking.Payment;
import main.backend.model.user.Customer;
import main.backend.service.BookingService;
import main.backend.service.CustomerService;
import main.backend.service.PaymentService;
import main.frontend.ui.table.model.BookingTableModel;
import main.frontend.util.SwingUtils;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class AdminBookingPage extends BasePage {
    private JTable bookingTable;
    private JComboBox<String> customerComboBox;
    private JButton backButton;
    private JButton makePaymentButton;
    private JPanel mainPanel;
    private JButton openReceiptButton;
    private BookingTableModel bookingTableModel;

    private final BookingService bookingService;
    private final PaymentService paymentService;
    private final CustomerService customerService;
    private DefaultComboBoxModel<String> comboBoxModel;

    public AdminBookingPage(Application application, BookingService bookingService, CustomerService customerService, PaymentService paymentService) {
        super(application);
        this.bookingService = bookingService;
        this.customerService = customerService;
        this.paymentService = paymentService;

        this.backButton.addActionListener(this::backToPreviousPage);
        this.openReceiptButton.addActionListener(e -> openReceipt());
        makePaymentButton.addActionListener(e -> makePayment());
        bookingTable.getSelectionModel().addListSelectionListener(e -> onItemSelected());
        customerComboBox.addActionListener(e -> filterByCustomer());
    }

    /** functions to open receipt interface*/
    private void openReceipt() {
        Booking selectedBooking = getSelectedBooking();
        if (selectedBooking.getStatus().equals(BookingStatus.PENDING_PAYMENT)) {
            SwingUtils.promptMessageError("Booking has not been paid yet!");
            return;
        }

        application.toPaymentDetailPage(selectedBooking);
    }

    /** function to make payment
     * if booking confirmed, return error message
     * else change statement to paid
     */
    private void makePayment() {
        Booking selectedBooking = getSelectedBooking();
        if (selectedBooking.getStatus().equals(BookingStatus.CONFIRMED)) {
            SwingUtils.promptMessageError("Payment already made!");
            return;
        }

        SwingUtils.promptMessageInfo("Payment done!");
        bookingService.paymentDone(selectedBooking.getBookingId());
        paymentService.makePayment(Payment.initialize(selectedBooking.getBookingId(), selectedBooking.getTotalPrice()));
        refreshData();
    }

    /** @return table of selected booking*/
    private Booking getSelectedBooking() {
        int selectedRow = bookingTable.getSelectedRow();
        Booking selectedBooking = bookingTableModel.getModelAt(selectedRow);
        return selectedBooking;
    }

    /** disable payment & open receipt button when no row selected
     * enable when row is selected
     */
    private void onItemSelected() {
        int[] selectedRows = bookingTable.getSelectedRows();
        if (selectedRows.length != 1){
            makePaymentButton.setEnabled(false);
            openReceiptButton.setEnabled(false);
            return;
        }

        makePaymentButton.setEnabled(true);
        openReceiptButton.setEnabled(true);
    }

    /** filter customer
     * default all customer is shown if not specified
     */
    private void filterByCustomer() {
        String selectedCustomer = (String) customerComboBox.getSelectedItem();

        if ("All".equals(selectedCustomer)) {
            refreshTable(bookingService.getAllBookings());
            return;
        }


        List<Booking> bookings = bookingService.getAllBookingsByCustomer(selectedCustomer);
        refreshTable(bookings);
    }

    /** generate table functions
     */
    private void createUIComponents() {
        bookingTableModel = new BookingTableModel();
        bookingTable = new JTable(bookingTableModel);

        this.comboBoxModel = new DefaultComboBoxModel<>();
        customerComboBox = new JComboBox<>(comboBoxModel);
    }

    /** refreshing data
     * refreshing table*/
    @Override
    public void refreshData() {
        refreshTable(bookingService.getAllBookings());

        comboBoxModel.removeAllElements();
        comboBoxModel.addElement("All");
        comboBoxModel.addAll(customerService.getAllCustomers().stream().map(Customer::getUsername).collect(Collectors.toList()));
        comboBoxModel.setSelectedItem("All");
        customerComboBox.addNotify();
    }

    private void refreshTable(List<Booking> bookingService) {
        bookingTableModel.refreshData(bookingService);
        bookingTable.addNotify();
        bookingTable.clearSelection();
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }
}
