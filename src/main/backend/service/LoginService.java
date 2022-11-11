package main.backend.service;

import main.backend.model.user.User;

import java.util.Optional;

public interface LoginService {
    Optional<? extends User> login(String username, String password);
}
