package com.fernandovalente.services.repository;

import com.fernandovalente.services.model.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Long> {
}
