package com.fernandovalente.services.repository;

import com.fernandovalente.services.model.Booking;
import com.fernandovalente.services.model.Customer;
import com.fernandovalente.services.model.Stylist;
import com.fernandovalente.services.model.TimeSlot;
import com.fernandovalente.services.service.TimeSlotCalculatorService;
import org.javatuples.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.INTERNAL;
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
public class TimeSlotRepositoryTest {

    @Autowired
    private StylistRepository stylistRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Test
    public void shouldReturnZeroTimeSlotsIfThereIsOneStylistNotWithoutBookings() {

        LocalDate from = LocalDate.now();
        LocalDate to = from.plusDays(2);

        Stylist firstStylist = new Stylist("first Stylist");
        stylistRepository.save(firstStylist);

        List<Object[]> totallyBooked = timeSlotRepository.findTimeSlotsTotallyBooked(from, to);

        assertThat(totallyBooked).hasSize(0);
    }

    @Test
    public void shouldReturnProperTimeSlotsIfThereIsOneStylistAndManyBookings() {

        LocalDate from = LocalDate.now();
        LocalDate to = from;

        Customer customer = new Customer("Only one customer");
        customerRepository.save(customer);

        Stylist firstStylist = new Stylist("first Stylist");
        stylistRepository.save(firstStylist);

        for (int slot = 0; slot < TimeSlot.MAX_TIME_SLOT_PER_DAY; slot++) {
            TimeSlot timeSlot = new TimeSlot(slot, from);
            timeSlotRepository.save(timeSlot);

            Booking booking = new Booking(firstStylist, timeSlot, customer);
            List<Booking> bookings = new ArrayList<>();
            bookings.add(booking);
            firstStylist.setBookings(bookings);

            bookingRepository.save(booking);

            List<Object[]> totallyBooked = timeSlotRepository.findTimeSlotsTotallyBooked(from, to);

            assertThat(totallyBooked).hasSize(slot + 1);
        }
    }

    @Test
    public void shouldReturnZeroTimeSlotsIfThereAreTwoStylistButNoBusyTimeSlots() {

        LocalDate from = LocalDate.now();
        LocalDate to = from;

        Customer customer = new Customer("Only one customer");
        customerRepository.save(customer);

        Stylist firstStylist = new Stylist("first Stylist");
        stylistRepository.save(firstStylist);

        Stylist secondStylist = new Stylist("second Stylist");
        stylistRepository.save(secondStylist);

        for (int slot = 0; slot < TimeSlot.MAX_TIME_SLOT_PER_DAY; slot++) {
            Stylist current = slot < TimeSlot.MAX_TIME_SLOT_PER_DAY / 2 ? firstStylist : secondStylist;
            TimeSlot timeSlot = new TimeSlot(slot, from);
            timeSlotRepository.save(timeSlot);

            Booking booking = new Booking(current, timeSlot, customer);
            List<Booking> bookings = new ArrayList<>();
            bookings.add(booking);
            current.setBookings(bookings);

            bookingRepository.save(booking);
        }

        List<Object[]> totallyBooked = timeSlotRepository.findTimeSlotsTotallyBooked(from, to);
        assertThat(totallyBooked).hasSize(0);
    }

    @Test
    public void shouldReturn8TimeSlotsIfThereAreTwoStylistAndBothAreBookedFor8TimeSlots() {

        LocalDate from = LocalDate.now();
        LocalDate to = from;

        Customer customer = new Customer("Only one customer");
        customerRepository.save(customer);

        Stylist firstStylist = new Stylist("first Stylist");
        stylistRepository.save(firstStylist);

        Stylist secondStylist = new Stylist("second Stylist");
        stylistRepository.save(secondStylist);

        for (int slot = 0; slot < TimeSlot.MAX_TIME_SLOT_PER_DAY; slot++) {

            TimeSlot timeSlot = new TimeSlot(slot, from);
            timeSlotRepository.save(timeSlot);

            Booking booking = new Booking(firstStylist, timeSlot, customer);
            List<Booking> bookings = new ArrayList<>();
            bookings.add(booking);
            firstStylist.setBookings(bookings);
            bookingRepository.save(booking);

            if (slot < TimeSlot.MAX_TIME_SLOT_PER_DAY / 2) {
                Booking secondBooking = new Booking(secondStylist, timeSlot, customer);
                List<Booking> secondBookings = new ArrayList<>();
                secondBookings.add(secondBooking);
                secondStylist.setBookings(secondBookings);
                bookingRepository.save(secondBooking);
            }
        }

        List<Object[]> totallyBooked = timeSlotRepository.findTimeSlotsTotallyBooked(from, to);
        assertThat(totallyBooked).hasSize(8);
    }
}
