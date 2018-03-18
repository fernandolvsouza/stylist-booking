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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional(readOnly = true)
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

    @Transactional
    public Booking book(Long customerId, TimeSlot timeSlot) {
        Optional<Customer> persistedCustomer = customerRepository.findById(customerId);

        if (!persistedCustomer.isPresent()) {
            throw new IllegalArgumentException("CustomerId " + customerId + "does not exist ");
        }

        Optional<TimeSlot> persistedTimeSlot = timeSlotRepository.findByDayAndDaySlot(timeSlot.getDay(),
                timeSlot.getDaySlot());
        if (!persistedTimeSlot.isPresent()) {
            timeSlotRepository.save(timeSlot);
        }


        Stylist stylist = stylistAvailabilityService.findAvailableStylist(timeSlot);
        Booking booking = new Booking(stylist, timeSlot, persistedCustomer.get());
        return bookingRepository.save(booking);
    }

    public Iterable<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
