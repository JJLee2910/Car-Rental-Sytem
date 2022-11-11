package main.frontend.ui;

import main.Application;
import main.backend.model.car.Car;
import main.backend.model.car.Transmission;
import main.backend.service.CarService;
import main.frontend.exception.ValidationException;
import main.frontend.util.SwingUtils;
import main.frontend.util.ValidatorUtils;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.function.BiConsumer;

public class CarPage extends BasePage {
    private JPanel mainPanel;
    private JTextField plateNumberField;
    private JTextField brandField;
    private JTextField modelField;
    private JTextField seatsField;
    private JComboBox transmissionComboBox;
    private JTextField priceField;
    private JButton proceedButton;
    private JButton backButton;
    private JLabel title;

    private final CarService carService;
    private final Mode mode;

    public CarPage(Application application, CarService carService, Mode mode) {
        super(application);
        this.carService = carService;
        this.mode = mode;

        this.title.setText(mode.title);

        plateNumberField.setEnabled(mode.primaryKeyFieldEnabled);

        backButton.addActionListener(this::backToPreviousPage);
        initializeProceedButton(mode.proceedButtonName, mode.proceedAction);
    }


    /** initialize button*/
    private void initializeProceedButton(String proceedButtonName, BiConsumer<CarService, Car> proceedAction) {
        proceedButton.setText(proceedButtonName);
        proceedButton.addActionListener(e -> addCar(proceedAction));
    }

    /** validate car data
     * add car if validate success*/
    private void addCar(BiConsumer<CarService, Car> proceedAction) {
        try {
            validateData();

            Car car = retrieveCar();
            proceedAction.accept(carService, car);

            onAddCarSuccess(car);
        } catch (ValidationException e) {
            onAddCarFail(e);
        }
    }

    /**
     * error message when add car fail
     **/
    private void onAddCarFail(ValidationException e) {
        SwingUtils.promptMessageError(e.getMessage());
    }

    private void onAddCarSuccess(Car car) {
        SwingUtils.promptMessageInfo(mode.successMessage);
        backToPreviousPage();
    }

    /** retrieve car data*/
    private Car retrieveCar() {
        return new Car(plateNumberField.getText(), brandField.getText(), modelField.getText(),
                Integer.parseInt(seatsField.getText()), new BigDecimal(priceField.getText()),
                Transmission.valueOf(transmissionComboBox.getSelectedItem().toString()));
    }

    /** car data validation
     * prompt error message with different validation fucntions
     **/
    private void validateData() throws ValidationException {
        validateCarPlate(plateNumberField.getText());
        validateBrand(brandField.getText());
        validateModel(modelField.getText());
        validateSeats(seatsField.getText());
        validatePrice(priceField.getText());
    }

    private void validatePrice(String text) throws ValidationException {
        ValidatorUtils.validateIsCurrency(text, "Price format invalid!");
    }

    private void validateSeats(String text) throws ValidationException {
        ValidatorUtils.validateIsInteger(text, "Seat is not number!");

    }

    private void validateModel(String text) throws ValidationException {
        ValidatorUtils.validateNotEmpty(text, "Model empty!");
    }

    private void validateBrand(String text) throws ValidationException {
        ValidatorUtils.validateNotEmpty(text, "Brand empty!");
    }

    private void validateCarPlate(String text) throws ValidationException {
        ValidatorUtils.validateNotEmpty(text, "Plate No. empty!");

        if (mode.equals(Mode.ADD) && carService.findByCarPlate(text).isPresent())
            throw new ValidationException("Car exists!");
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public void refreshData() {
        plateNumberField.setText("");
        brandField.setText("");
        modelField.setText("");
        seatsField.setText("");
        priceField.setText("");
    }

    public void initializeCarPage(Car car) {
        plateNumberField.setText(car.getCarPlate());
        brandField.setText(car.getBrand());
        modelField.setText(car.getModel());
        seatsField.setText(car.getSeats().toString());
        priceField.setText(car.getPrice().toString());
        transmissionComboBox.setSelectedIndex(car.getTransmission().ordinal());
    }

    public enum Mode {
        ADD("Add New Car", "Add", "Add car success!", (service, car) -> service.addCar(car), true),
        EDIT("Edit Car", "Edit", "Edit car success!", (service, car) -> service.updateCar(car), false);

        private final String title;
        private final String proceedButtonName;
        private final BiConsumer<CarService, Car> proceedAction;
        public final boolean primaryKeyFieldEnabled;
        public final String successMessage;


        Mode(String title, String proceedButtonName, String successMessage, BiConsumer<CarService, Car> proceedAction, boolean primaryKeyFieldEnabled) {
            this.title = title;
            this.proceedButtonName = proceedButtonName;
            this.proceedAction = proceedAction;
            this.primaryKeyFieldEnabled = primaryKeyFieldEnabled;
            this.successMessage = successMessage;
        }
    }
}
