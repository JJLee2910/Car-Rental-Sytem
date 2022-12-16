package main.frontend.ui;

import main.Application;
import main.backend.model.booking.Payment;
import main.backend.service.PaymentService;
import main.frontend.ui.table.model.PaymentTableModel;
import main.frontend.util.DatePickerUtils;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class DailySalesReportPage extends BasePage {
    private JPanel mainPanel;
    private JDatePickerImpl paymentDatePicker;
    private JButton OKButton;
    private JTable paymentTable;
    private JLabel totalField;

    private final PaymentService paymentService;
    private PaymentTableModel paymentTableModel;

    public DailySalesReportPage(Application application, PaymentService paymentService) {
        super(application);
        this.paymentService = paymentService;

        OKButton.addActionListener(this::backToPreviousPage);
        paymentDatePicker.addActionListener(e -> this.loadData());
    }

    /** load payment data*/
    private void loadData() {
        LocalDate selectedDate = DatePickerUtils.getDateFromDatePicker(paymentDatePicker);
        List<Payment> payments = paymentService.getPaymentsByDate(selectedDate);
        paymentTableModel.refreshData(payments);
        paymentTable.addNotify();
        setTotal(payments.stream().map(Payment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    /** convert all sales into total*/
    private void setTotal(BigDecimal total) {
        totalField.setText(String.format("Total: RM %s", total.setScale(2, RoundingMode.HALF_UP)));
    }

    private void createUIComponents() {
        paymentTableModel = new PaymentTableModel();
        paymentTable = new JTable(paymentTableModel);
        paymentDatePicker = DatePickerUtils.createNewDatePicker();
    }

    @Override
    public void refreshData() {
        DatePickerUtils.setDatePickerSelectedDate(paymentDatePicker, LocalDate.now());
        loadData();
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }
}
