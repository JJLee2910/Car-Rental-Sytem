package main.backend.service;

import main.backend.db.repository.AdminRepository;
import main.backend.db.repository.CustomerRepository;
import main.backend.model.user.Admin;
import main.backend.model.user.User;

import java.util.Optional;

public class LoginServiceImpl implements LoginService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;

    public LoginServiceImpl(AdminRepository adminRepository, CustomerRepository customerRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<? extends User> login(String username, String password) {
        Optional<Admin> admin = adminRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));

        if (admin.isPresent())
            return admin;

        return customerRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));

    }
}
