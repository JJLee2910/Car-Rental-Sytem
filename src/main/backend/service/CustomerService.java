package main.backend.service;

import main.backend.model.user.Customer;
import main.frontend.exception.ValidationException;

import java.util.List;
import java.util.Optional;

public interface CustomerService extends UserService {
    void registerCustomer(Customer customer) throws ValidationException;
    Customer getByName(String customer);
    List<Customer> getAllCustomers();
    Optional<Customer> findByName(String text);
    void updateCustomer(Customer customer);
}
