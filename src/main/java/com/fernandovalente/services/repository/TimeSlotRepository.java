package com.fernandovalente.services.repository;

import com.fernandovalente.services.model.TimeSlot;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Optional;

public interface TimeSlotRepository extends CrudRepository<TimeSlot, Long> {
    Iterable<TimeSlot> findByDayBetween(LocalDate from, LocalDate to);
    Optional<TimeSlot> findByDayAndDaySlot(LocalDate day, @Min(0) @Max(15) int daySlot);
}
