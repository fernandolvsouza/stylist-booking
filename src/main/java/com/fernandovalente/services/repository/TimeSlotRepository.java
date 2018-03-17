package com.fernandovalente.services.repository;

import com.fernandovalente.services.model.TimeSlot;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface TimeSlotRepository extends CrudRepository<TimeSlot, Long> {
    Iterable<TimeSlot> findByDayBetween(LocalDate from, LocalDate to);
}
