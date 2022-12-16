package main.frontend.ui;

import main.backend.session.SessionContainer;

public interface LogOutable extends Backable {
    SessionContainer getSessionContainer();

    default void logOut() {
        getSessionContainer().setCurrentUser(null);
        backToPreviousPage();
    }
}
