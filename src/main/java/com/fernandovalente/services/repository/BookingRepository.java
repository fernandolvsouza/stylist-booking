package com.fernandovalente.services.repository;

import com.fernandovalente.services.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface BookingRepository extends CrudRepository<Booking, Long> {
}
