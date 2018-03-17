package com.fernandovalente.services.service;

import com.fernandovalente.services.model.Booking;
import com.fernandovalente.services.model.Customer;
import com.fernandovalente.services.model.Stylist;
import com.fernandovalente.services.model.TimeSlot;
import com.fernandovalente.services.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class BookingService {
    @Autowired
    private StylistAvailabilityService stylistAvailabilityService;

    @Autowired
    private BookingRepository bookingRepository;

    public Booking book(Customer customer, Set<TimeSlot> timeSlots) {
        Stylist stylist = stylistAvailabilityService.findAvailableStylist(timeSlots);

        // TODO avoid overbooking
        Booking booking = new Booking(stylist, timeSlots, customer);

        return bookingRepository.create(booking);
    }

    public List<Booking> getAllStylistBooking() {
        return bookingRepository.getAll();
    }
}
