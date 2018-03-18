package com.fernandovalente.services.repository;

import com.fernandovalente.services.model.TimeSlot;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotRepository extends CrudRepository<TimeSlot, Long> {
    @Query("SELECT t.day, t.daySlot FROM Stylist s JOIN s.bookings b JOIN b.timeSlot t WHERE s.state = 1 AND t.day >= ?1 " +
            "AND t.day <= ?2 GROUP BY t.day, t.daySlot HAVING count(s) >= (SELECT count(s) FROM Stylist s WHERE s.state = 1)")
    List<Object[]> findTimeSlotsTotallyBooked(LocalDate fromInclusive, LocalDate toInclusive);
}
