package main.frontend.ui;

import main.Application;
import main.backend.model.user.Customer;
import main.backend.session.SessionContainer;

import javax.swing.*;

public class UserMenuPage extends BasePage implements LogOutable {
    private JPanel mainPanel;
    private JButton bookACarButton;
    private JButton bookingHistoryButton;
    private JButton logOutButton;
    private JButton updateInfoButton;

    public UserMenuPage(Application application) {
        super(application);

        logOutButton.addActionListener(e -> this.logOut());
        bookACarButton.addActionListener(e -> application.toBookCar());
        bookingHistoryButton.addActionListener(e -> application.toCustomerBookingHistory());
        updateInfoButton.addActionListener(e -> application.toCustomerEdit((Customer) application.getSessionContainer().getSession().getCurrentUser()));
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public SessionContainer getSessionContainer() {
        return application.getSessionContainer();
    }
}
