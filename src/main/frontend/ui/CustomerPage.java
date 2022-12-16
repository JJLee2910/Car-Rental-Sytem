package main.frontend.ui;

import main.Application;
import main.backend.model.user.Customer;
import main.backend.model.user.Gender;
import main.frontend.exception.ValidationException;
import main.frontend.util.SwingUtils;
import main.frontend.util.ValidatorUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CustomerPage extends BasePage {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox genderComboBox;
    private JTextField ageField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField addressField;
    private JButton submitButton;
    private JButton backButton;
    private JPanel  mainPanel;

    private final SuccessAction successAction;

    public CustomerPage(Application application, SuccessAction successAction, boolean usernameEnabled) {
        super(application);
        this.successAction = successAction;

        usernameField.setEnabled(usernameEnabled);

        backButton.addActionListener(this::backToPreviousPage);
        submitButton.addActionListener(this::registerCustomer);
    }

    /** register customer*/
    private void registerCustomer(ActionEvent event) {
        try {
            validateData();

            Customer customer = retrieveCustomer();
            successAction.doAction(customer);

            onRegisterSuccess(customer);
        } catch (ValidationException e) {
            onRegisterFail(e);
        }
    }


    /** prompt when customer register success*/
    private void onRegisterSuccess(Customer customer) {
        SwingUtils.promptMessageInfo("Success!");
        application.getSessionContainer().setCurrentUser(customer);
        backToPreviousPage();
    }

    /** prompt error message when register fail*/
    private void onRegisterFail(ValidationException e) {
        SwingUtils.promptMessageError(e.getMessage());
    }

    /** receive customer register data*/
    private Customer retrieveCustomer() {
        return new Customer(usernameField.getText(), String.valueOf(passwordField.getPassword()),
                Gender.valueOf(genderComboBox.getSelectedItem().toString()), Integer.parseInt(ageField.getText()),
                phoneField.getText(), emailField.getText(), addressField.getText());
    }

    /** data format/primary validation
     * 1. validate data
     * 2. validate address
     * 3. validate email
     * 4. validate phone
     * 5. validate age
     * 6. validate password
     * 7. validate username
     */
    private void validateData() throws ValidationException {
        validateUsername(usernameField.getText());
        validatePassword(String.valueOf(passwordField.getPassword()));
        validateAge(ageField.getText());
        validatePhone(phoneField.getText());
        validateEmail(emailField.getText());
        validateAddress(addressField.getText());
    }

    private void validateAddress(String text) throws ValidationException {
        ValidatorUtils.validateNotEmpty(text, "Address empty!");
    }

    private void validateEmail(String text) throws ValidationException {
        ValidatorUtils.validateNotEmpty(text, "Email empty!");
        ValidatorUtils.validateMatchRegex(text, "^[A-Za-z0-9+_.-]+@(.+)$", "Illegal email!");
    }

    private void validatePhone(String text) throws ValidationException {
        ValidatorUtils.validateNotEmpty(text, "Phone number empty!");
        ValidatorUtils.validateMatchRegex(text, "^(\\+?6?01)[0-46-9]-*[0-9]{7,8}$", "Illegal phone number!");
    }

    private void validateAge(String text) throws ValidationException {
        ValidatorUtils.validateNotEmpty(text, "Age empty!");
        ValidatorUtils.validateIsInteger(text, "Age is not number!");

        int age = Integer.parseInt(text);
        if (age <= 0 | age > 150)
            throw new ValidationException("Invalid age range!");
    }

    private void validatePassword(String text) throws ValidationException {
        ValidatorUtils.validateNotEmpty(text, "Password empty!");
    }

    private void validateUsername(String text) throws ValidationException {
        ValidatorUtils.validateNotEmpty(text, "Username empty!");
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public void refreshData() {
        usernameField.setText("");
        passwordField.setText("");
        ageField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
    }

    public void initializeData(Customer customer) {
        usernameField.setText(customer.getUsername());
        passwordField.setText(customer.getPassword());
        ageField.setText(customer.getAge().toString());
        phoneField.setText(customer.getPhone());
        emailField.setText(customer.getEmail());
        addressField.setText(customer.getAddress());
    }

    @FunctionalInterface
    public interface SuccessAction {
        void doAction(Customer customer) throws ValidationException;
    }
}
