package com.fernandovalente.services.repository;

import com.fernandovalente.services.model.Booking;
import com.fernandovalente.services.model.Customer;
import com.fernandovalente.services.model.Stylist;
import com.fernandovalente.services.model.TimeSlot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class StylistRepositoryTest {

    @Autowired
    private StylistRepository stylistRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Test
    public void shouldReturnOneStylistIfThereIsOnlyOne() {
        Stylist stylist = new Stylist("Only one");
        stylistRepository.save(stylist);

        List<Stylist> availableStylists = stylistRepository.findStylistsWithoutTimeSlotBooked(LocalDate.now(), 0);
        assertThat(availableStylists.get(0)).isEqualTo(stylist);
    }

    @Test
    public void shouldReturnZeroStylistsIfTheyAreAllBooked() {
        Stylist stylist = new Stylist("Only one");
        stylistRepository.save(stylist);

        Customer customer = new Customer("Only one customer");
        customerRepository.save(customer);

        TimeSlot timeSlot = new TimeSlot(0, LocalDate.now());
        timeSlotRepository.save(timeSlot);

        Booking booking = new Booking(stylist, timeSlot, customer);
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        stylist.setBookings(bookings);

        bookingRepository.save(booking);

        List<Stylist> availableStylists = stylistRepository.findStylistsWithoutTimeSlotBooked(timeSlot.getDay(),
                timeSlot.getDaySlot());
        assertThat(availableStylists).hasSize(0);
    }

    @Test
    public void shouldReturn5StylistsFromTotal10() {
        Customer customer = new Customer("Only one customer");
        customerRepository.save(customer);


        TimeSlot timeSlot = new TimeSlot(0, LocalDate.now());
        timeSlotRepository.save(timeSlot);

        int total = 10;
        for (int index = 0; index < total/2; index ++) {
            Stylist stylist = new Stylist("Stylist " + index);
            stylistRepository.save(stylist);

            Booking booking = new Booking(stylist, timeSlot, customer);
            List<Booking> bookings = new ArrayList<>();
            bookings.add(booking);
            stylist.setBookings(bookings);
            bookingRepository.save(booking);
        }

        for (int index = total/2; index < total; index ++) {
            Stylist stylist = new Stylist("Stylist " + index);
            stylistRepository.save(stylist);
        }

        List<Stylist> availableStylists = stylistRepository.findStylistsWithoutTimeSlotBooked(timeSlot.getDay(),
                timeSlot.getDaySlot());
        assertThat(availableStylists).hasSize(5);
    }
}
