package main.backend.session;

import main.backend.model.user.User;

public interface SessionContainer {
    Session getSession();

    void setCurrentUser(User user);

}
