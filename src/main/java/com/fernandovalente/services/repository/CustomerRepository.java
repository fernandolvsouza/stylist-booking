package com.fernandovalente.services.repository;

import com.fernandovalente.services.model.Booking;
import com.fernandovalente.services.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
