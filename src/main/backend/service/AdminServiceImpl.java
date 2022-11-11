package main.backend.service;

import main.backend.db.repository.AdminRepository;
import main.backend.model.user.User;

import java.util.Optional;

public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Optional<? extends User> findByUsername(String userName) {
        return adminRepository.findByUsername(userName);
    }
}
