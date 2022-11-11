package main.frontend.ui;

import main.Application;
import main.backend.model.booking.Booking;
import main.backend.model.car.Car;
import main.backend.service.BookingService;
import main.backend.service.CarService;
import main.frontend.exception.ValidationException;
import main.frontend.ui.table.model.CarTableModel;
import main.frontend.util.DatePickerUtils;
import main.frontend.util.SwingUtils;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class CarBookingPage extends BasePage {
    private JPanel mainPanel;
    private JButton backButton;
    private JButton bookButton;
    private JTable carTable;
    private JDatePickerImpl startDatePicker;
    private JDatePickerImpl endDatePicker;
    private JButton searchButton;
    private JLabel totalPriceField;

    private CarTableModel carTableModel;

    private BookingState bookingState;

    private CarService carService;
    private BookingService bookingService;

    public CarBookingPage(Application application, CarService carService, BookingService bookingRepository) {
        super(application);
        this.carService = carService;
        this.bookingService = bookingRepository;

        backButton.addActionListener(this::backToPreviousPage);
        searchButton.addActionListener(this::searchValidCarList);
        bookButton.addActionListener(this::bookCar);

        carTable.getSelectionModel().addListSelectionListener(e -> onItemSelected());
    }

    private void bookCar(ActionEvent event) {
        List<Car> selectedCars = getSelectedCars();

        LocalDate startDate = DatePickerUtils.getDateFromDatePicker(startDatePicker);
        LocalDate endDate = DatePickerUtils.getDateFromDatePicker(endDatePicker);

        selectedCars.stream()
                .map(car -> Booking.initializeBooking(
                        application.getSessionContainer().getSession().getCurrentUser().getUsername(),
                        car.getCarPlate(), startDate, endDate, getTotalPrice()))
                .forEach(bookingService::makeBooking);

        SwingUtils.promptMessageInfo("Booking has been made! Please wait for the booking confirmation!");
        refreshData();
    }



    private void onItemSelected() {
        List<Car> selectedCars = getSelectedCars();

        if (bookingState.equals(BookingState.DISALLOWED))
            return;

        LocalDate startDate = DatePickerUtils.getDateFromDatePicker(startDatePicker);
        LocalDate endDate = DatePickerUtils.getDateFromDatePicker(endDatePicker);
        /* getting the days including the rental end date
        */
        long days = DAYS.between(startDate, endDate) + 1;

        BigDecimal unitPrice = selectedCars.stream().map(Car::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(days)).setScale(2, RoundingMode.HALF_UP);
        setTotalPrice(totalPrice);
    }

    private List<Car> getSelectedCars() {
        int[] selectedRows = carTable.getSelectedRows();
        List<Car> selectedCars = carTableModel.getModelAt(selectedRows);
        return selectedCars;
    }

    private void setBookingState(BookingState state) {
        bookingState = state;
        state.onStatusChange(this);
    }

    private void searchValidCarList(ActionEvent event) {
        LocalDate startDate = DatePickerUtils.getDateFromDatePicker(startDatePicker);
        LocalDate endDate = DatePickerUtils.getDateFromDatePicker(endDatePicker);

        try {
            if (startDate == null || endDate == null)
                throw new ValidationException("Start date and end date cannot be empty!");

            if (startDate.compareTo(LocalDate.now()) < 0)
                throw new ValidationException("Start date cannot be earlier than current date!");

            if (startDate.compareTo(endDate) > 0) {
                throw new ValidationException("Start date must earlier than end date!");
            }


        } catch (ValidationException e) {
            SwingUtils.promptMessageError(e.getMessage());
            return;
        }

        filterBookedCar(bookingService.getBookingsWithinDates(startDate, endDate));
        setBookingState(BookingState.ALLOWED);
        carTable.clearSelection();
    }

    private void filterBookedCar(List<Booking> bookingBetween) {
        Set<String> bookedCarPlate = bookingBetween.stream().map(Booking::getCarPlate).collect(Collectors.toSet());

        List<Car> availableCarList = carService.getAllCarsExcept(bookedCarPlate);

        carTableModel.refreshData(availableCarList);
        carTable.addNotify();
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        startDatePicker = DatePickerUtils.createNewDatePicker();
        endDatePicker = DatePickerUtils.createNewDatePicker();
        carTableModel = new CarTableModel();
        carTable = new JTable(carTableModel);
    }

    @Override
    public void refreshData() {
        setBookingState(BookingState.DISALLOWED);
        DatePickerUtils.refreshDatePicker(startDatePicker);
        DatePickerUtils.refreshDatePicker(endDatePicker);
        setTotalPrice(BigDecimal.ZERO);
        carTable.clearSelection();
        carTableModel.refreshData(carService.getAllCars());
        carTable.addNotify();
    }

    private void setTotalPrice(BigDecimal price) {
        totalPriceField.setText(String.format("Total: RM %s", price.setScale(2, RoundingMode.HALF_UP)));
    }

    private BigDecimal getTotalPrice() {
        return new BigDecimal(totalPriceField.getText().substring(10));
    }

    private enum BookingState {
        DISALLOWED(false),
        ALLOWED(true);

        private final boolean bookingAllowed;

        BookingState(boolean bookingAllowed) {
            this.bookingAllowed = bookingAllowed;
        }

        public void onStatusChange(CarBookingPage page) {
            page.bookButton.setEnabled(bookingAllowed);
        }
    }
}
