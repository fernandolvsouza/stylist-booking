package com.fernandovalente.services.service;

import com.fernandovalente.services.model.Customer;
import com.fernandovalente.services.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    public Customer register(@Valid Customer customer) {
        return repository.save(customer);
    }
}
