package com.fernandovalente.services.service;

import com.fernandovalente.services.model.Booking;
import com.fernandovalente.services.model.Customer;
import com.fernandovalente.services.model.Stylist;
import com.fernandovalente.services.model.TimeSlot;
import com.fernandovalente.services.repository.BookingRepository;
import com.fernandovalente.services.repository.CustomerRepository;
import com.fernandovalente.services.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BookingService {
    @Autowired
    private StylistAvailabilityService stylistAvailabilityService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    public Booking book(Customer customer, Set<TimeSlot> timeSlots) {
        Stylist stylist = stylistAvailabilityService.findAvailableStylist(timeSlots);
        timeSlots.forEach((timeSlot -> timeSlotRepository.save(timeSlot)));
        customerRepository.save(customer);
        // TODO avoid overbooking
        Booking booking = new Booking(stylist, timeSlots, customer);

        return bookingRepository.save(booking);
    }

    public Iterable<Booking> getAllStylistBooking() {
        return bookingRepository.findAll();
    }
}
