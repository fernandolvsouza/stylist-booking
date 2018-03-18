package com.fernandovalente.services.repository;

import com.fernandovalente.services.model.Stylist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface StylistRepository extends CrudRepository<Stylist, Long> {
    @Query("SELECT s FROM Stylist s WHERE s NOT IN (" +
            "SELECT s FROM Stylist s JOIN s.bookings b JOIN b.timeSlot t WHERE t.day = ?1 AND t.daySlot = ?2" +
            ")")
    List<Stylist> findStylistsWithoutTimeSlotBooked(LocalDate date, int daySlot);
}
