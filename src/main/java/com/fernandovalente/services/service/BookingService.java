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

@Component
public class BookingService {

    private StylistAvailabilityService stylistAvailabilityService;
    private BookingRepository bookingRepository;
    private CustomerRepository customerRepository;
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    public BookingService(StylistAvailabilityService stylistAvailabilityService, BookingRepository bookingRepository,
                          CustomerRepository customerRepository, TimeSlotRepository timeSlotRepository) {
        this.stylistAvailabilityService = stylistAvailabilityService;
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    public Booking book(Customer customer, TimeSlot timeSlot) {
        Stylist stylist = stylistAvailabilityService.findAvailableStylist(timeSlot);
        timeSlotRepository.save(timeSlot);
        customerRepository.save(customer);
        // TODO avoid overbooking
        Booking booking = new Booking(stylist, timeSlot, customer);
        return bookingRepository.save(booking);
    }

    public Iterable<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
