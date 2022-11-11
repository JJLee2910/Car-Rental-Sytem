package main.frontend.ui;

import main.Application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Optional;

public abstract class BasePage implements Backable {
    protected final Application application;
    protected Optional<BasePage> previousPage;

    public BasePage(Application application) {
        this.application = application;
    }

    public abstract JPanel getPanel();

    public void open() {
        refreshData();
        getPanel().setVisible(true);
    }

    public void close() {
        getPanel().setVisible(false);
        refreshData();
    }

    public void refreshData() {}

    @Override
    public void backToPreviousPage() {
        previousPage.ifPresent(application::backToPage);
    }

    public void backToPreviousPage(ActionEvent e) {
        backToPreviousPage();
    }

    public void setPreviousPage(BasePage page) {
        this.previousPage = Optional.ofNullable(page);
    }

    public Optional<BasePage> getPreviousPage() {
        return previousPage;
    }

    protected String getCurrentUserName() {
        return application.getSessionContainer().getSession().getCurrentUser().getUsername();
    }
}
