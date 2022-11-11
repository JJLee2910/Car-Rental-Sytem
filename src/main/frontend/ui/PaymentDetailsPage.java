package main.frontend.ui;

import main.Application;
import main.backend.model.booking.Booking;
import main.backend.model.car.Car;
import main.backend.model.user.Customer;
import main.backend.service.CarService;
import main.backend.service.CustomerService;
import main.utils.DateUtils;

import javax.swing.*;

public class PaymentDetailsPage extends BasePage {
    private JPanel mainPanel;
    private JTextField emailField;
    private JTextField carPlateField;
    private JTextField carBrandField;
    private JTextField carModelField;
    private JTextField carSeatsField;
    private JTextField carTransmissionField;
    private JButton OKButton;
    private JTextField bookingIdField;
    private JTextField bookingDateField;
    private JTextField bookingStartDateField;
    private JTextField bookingEndDateField;
    private JTextField totalPriceField;

    private final CustomerService customerService;
    private final CarService carService;

    public PaymentDetailsPage(Application application, CustomerService customerService, CarService carService) {
        super(application);
        this.customerService = customerService;
        this.carService = carService;

        OKButton.addActionListener(this::backToPreviousPage);
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    /** retrieve payments info
     * display in forms*/
    public void initializeData(Booking booking) {
        Customer customer = customerService.getByName(booking.getCustomer());
        Car car = carService.getByCarPlate(booking.getCarPlate());

        emailField.setText(customer.getEmail());

        carPlateField.setText(car.getCarPlate());
        carBrandField.setText(car.getBrand());
        carModelField.setText(car.getModel());
        carSeatsField.setText(car.getSeats().toString());
        carTransmissionField.setText(car.getTransmission().name());


        bookingIdField.setText(booking.getBookingId().toString());
        bookingDateField.setText(DateUtils.dateToString(booking.getCreated()));
        bookingStartDateField.setText(DateUtils.dateToString(booking.getStartDate()));
        bookingEndDateField.setText(DateUtils.dateToString(booking.getEndDate()));
        totalPriceField.setText(booking.getTotalPrice().toString());
    }
}
