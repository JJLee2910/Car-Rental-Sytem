package main.backend.service;

import main.backend.model.user.User;

import java.util.Optional;

public interface UserService {
    Optional<? extends User> findByUsername(String userName);
}
