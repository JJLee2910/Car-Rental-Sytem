package main.backend.session;

import main.backend.model.user.User;

public class SessionContainerImpl implements SessionContainer {
    private Session currentSession = new Session();

    @Override
    public Session getSession() {
        return currentSession;
    }

    @Override
    public void setCurrentUser(User user) {
        currentSession.setCurrentUser(user);
    }
}
