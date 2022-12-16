package main.backend.db.repository;

import main.backend.model.user.User;

import java.util.Optional;

public interface LoginRepository<T extends User> extends Repository<String, T> {
    Optional<T> findByUsername(String username);
}
