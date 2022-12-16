package main.backend.model.user;

public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password, Role.ADMIN);
    }
}
