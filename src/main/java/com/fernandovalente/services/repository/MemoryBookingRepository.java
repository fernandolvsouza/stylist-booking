package com.fernandovalente.services.repository;

import com.fernandovalente.services.model.Booking;
import com.fernandovalente.services.model.Customer;
import com.fernandovalente.services.model.Stylist;
import com.fernandovalente.services.model.TimeSlot;
import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class MemoryBookingRepository implements BookingRepository {
    private final static List<Booking> BOOKINGS_LIST = new ArrayList<>();

    public MemoryBookingRepository() {
        BOOKINGS_LIST.add(
                new Booking(
                        new Stylist("Stylist 1"),
                        ImmutableSet.of(new TimeSlot(0, LocalDate.now())),
                        new Customer("Customer 1")
                )
        );
    }

    public Booking create(Booking booking) {
        BOOKINGS_LIST.add(booking);
        return booking;
    }

    public List<Booking> getAll() {
        return BOOKINGS_LIST;
    }
}
