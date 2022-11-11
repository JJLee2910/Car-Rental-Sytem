package main.frontend.ui;

import main.Application;
import main.backend.session.SessionContainer;

import javax.swing.*;

public class AdminMenuPage extends BasePage implements LogOutable {
    private JButton carButton;
    private JButton bookingButton;
    private JButton dailySalesReportButton;
    private JButton logOutButton;
    private JPanel mainPanel;
    private JButton carMonthlyRevenueReportButton;

    public AdminMenuPage(Application application) {
        super(application);

        logOutButton.addActionListener(e -> this.logOut());
        carButton.addActionListener(e -> application.toCarMenu());
        bookingButton.addActionListener(e -> application.toAdminBookingPage());
        dailySalesReportButton.addActionListener(e -> application.toDailySalesReportPage());
        carMonthlyRevenueReportButton.addActionListener(e -> application.toCarMonthlyRevenueReportPage());
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
