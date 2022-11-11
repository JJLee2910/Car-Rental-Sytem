package main.frontend.ui;

import main.Application;
import main.backend.model.user.Role;
import main.backend.model.user.User;
import main.backend.service.LoginService;
import main.frontend.exception.ValidationException;
import main.frontend.util.SwingUtils;
import main.frontend.util.ValidatorUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Optional;

public class LoginPage extends BasePage {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton loginButton;
    private JPanel mainPanel;


    private final LoginService loginService;

    public LoginPage(Application application, LoginService loginService) {
        super(application);
        this.loginService = loginService;

        registerButton.addActionListener(e -> application.toCustomerRegister());
        initLoginButton();

    }

    @Override
    public void open() {
        super.open();
        setUpEnterToLogin();
    }

    private void setUpEnterToLogin() {
        mainPanel.getRootPane().setDefaultButton(loginButton);
    }

    private void initLoginButton() {
        loginButton.addActionListener(this::doLogin);
    }

    /** login validation*/
    private void doLogin(ActionEvent e) {
        try {
            ValidatorUtils.validateNotEmpty(usernameField.getText(), "Username blank!");
            ValidatorUtils.validateNotEmpty(String.valueOf(passwordField.getPassword()), "Password blank!");

            Optional<? extends User> user = loginService.login(usernameField.getText(), String.valueOf(passwordField.getPassword()));

            onLoginSuccess(user
                    .orElseThrow(()
                            -> new ValidationException("Wrong credential! Please try again!")
                    ));
        } catch (ValidationException ex) {
            onLoginFailure(ex.getMessage());
        }
    }

    /** prompt error message when login fail*/
    private void onLoginFailure(String errorMessage) {
        SwingUtils.promptMessageError(errorMessage);
        refreshData();
    }

    /** prompt message when login success*/
    private void onLoginSuccess(User user) {
        SwingUtils.promptMessageInfo("Login Success!");
        application.getSessionContainer().setCurrentUser(user);

        if (user.getRole().equals(Role.ADMIN)) {
            application.toAdminMenu();
            return;
        }

        application.toCustomerMenu();
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public void refreshData() {
        usernameField.setText("");
        passwordField.setText("");
    }
}
