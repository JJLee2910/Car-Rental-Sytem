package main.backend.service;

import main.backend.db.repository.AdminRepository;
import main.backend.db.repository.CustomerRepository;
import main.backend.model.user.Customer;
import main.backend.model.user.User;
import main.frontend.exception.ValidationException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, AdminRepository adminRepository) {
        this.customerRepository = customerRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public void registerCustomer(Customer customer) throws ValidationException {
        if (customerRepository.findByPrimaryKey(customer.getUsername()).isPresent() ||
                adminRepository.findByUsername(customer.getUsername()).isPresent())
            throw new ValidationException("User exists!");
        customerRepository.insert(customer);
    }

    @Override
    public Customer getByName(String customer) {
        return customerRepository.getByPrimaryKey(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.getLines().collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findByName(String text) {
        return customerRepository.findByPrimaryKey(text);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.update(customer);
    }

    @Override
    public Optional<? extends User> findByUsername(String userName) {
        return customerRepository.findByUsername(userName);
    }
}
