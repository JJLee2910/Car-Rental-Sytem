package main.frontend.ui;

import main.Application;
import main.backend.service.BookingService;
import main.backend.service.PaymentService;
import main.frontend.util.DatePickerUtils;
import main.frontend.util.PieChartUtils;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jfree.chart.ChartPanel;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class MonthlyCarRevenueReportPage extends BasePage {
    private ChartPanel chartPanel;
    private JDatePickerImpl monthPicker;
    private JButton OKButton;
    private JLabel totalField;
    private JPanel mainPanel;
    private DefaultPieDataset dataset;

    private final BookingService bookingService;
    private final PaymentService paymentService;

    public MonthlyCarRevenueReportPage(Application application, BookingService bookingService, PaymentService paymentService) {
        super(application);
        this.bookingService = bookingService;
        this.paymentService = paymentService;

        OKButton.addActionListener(this::backToPreviousPage);
        monthPicker.addActionListener(e -> onDateChange());
    }

    /** convert date change*/
    private void onDateChange() {
        Map<String, BigDecimal> carPlateRevenueMap = getCarPlateRevenueMap();
        populatePieChart(carPlateRevenueMap);
        BigDecimal total = carPlateRevenueMap.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        changeTotalRevenue(total);
    }

    /** map car plate*/
    private Map<String, BigDecimal> getCarPlateRevenueMap() {
        LocalDate date = DatePickerUtils.getDateFromDatePicker(monthPicker);
        HashMap<String, BigDecimal> mapData = paymentService.getAllPaymentsForMonth(date).stream()
                .map(payment -> bookingService.getById(payment.getBookingId()))
                .collect(HashMap::new, (map, booking) -> {
                    if (map.containsKey(booking.getCarPlate()))
                        map.replace(booking.getCarPlate(), map.get(booking.getCarPlate()).add(booking.getTotalPrice()));
                    map.putIfAbsent(booking.getCarPlate(), booking.getTotalPrice());
                }, HashMap::putAll);
        return mapData;
    }

    /** add pie chart*/
    private void populatePieChart(Map<String, BigDecimal> mapData ) {
        dataset.clear();
        mapData.forEach(dataset::setValue);
        chartPanel.addNotify();
    }

    /** set total revenue*/
    private void changeTotalRevenue(BigDecimal total) {
        totalField.setText(String.format("Total Revenue: RM %s", total.setScale(2, RoundingMode.HALF_UP)));
    }

    private void createUIComponents() {
        monthPicker = DatePickerUtils.createNewDatePicker();
        dataset = new DefaultPieDataset();
        chartPanel = new ChartPanel(PieChartUtils.generateNewPieChart(dataset));
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public void refreshData() {
        LocalDate today = LocalDate.now();
        DatePickerUtils.setDatePickerSelectedDate(monthPicker, today);
        onDateChange();
    }
}
