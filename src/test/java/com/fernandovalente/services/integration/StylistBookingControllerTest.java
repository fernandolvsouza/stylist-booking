package com.fernandovalente.services.integration;

import com.fernandovalente.services.model.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class StylistBookingControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_TEST_URL = "http://localhost";
    private static final String STYLIST_BOOKING_PATH = "/api/v1/stylist-booking";

    @Test
    public void shouldHaveHelloWordInHome() throws Exception {
        assertThat(this.restTemplate.getForObject(BASE_TEST_URL + ":" + port + "/",
                String.class)).contains("Hello World");
    }

    @Test
    public void shouldCreateBookingAndReturnIt() throws Exception {
        final TimeSlot timeSlot = new TimeSlot(0, LocalDate.now());
        final Set<TimeSlot> timeSlots = ImmutableSet.of(timeSlot);
        final Customer customer = new Customer("Customer 1");
        final BookingRequest bookingRequest = new BookingRequest(timeSlots, customer);

        final Booking expectedBooking = new Booking(new Stylist("chosen stylist"), timeSlots, customer);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BookingRequest> entity = new HttpEntity<>(bookingRequest, headers);
        assertThat(this.restTemplate.postForEntity(BASE_TEST_URL + ":" + port + STYLIST_BOOKING_PATH, entity,
                Booking.class).getBody()).isEqualToComparingFieldByFieldRecursively(expectedBooking);
    }

    @Test
    public void shouldShouldReturn400WhenThereIsNoCostumer() throws Exception {
        final JSONObject timeSlot = new JSONObject();
        timeSlot.put("daySlot", 0);
        timeSlot.put("day", "2018-03-17");

        final JSONArray timeSlots = new JSONArray(ImmutableList.of(timeSlot));
        final JSONObject noCustomer = new JSONObject();
        noCustomer.put("timeSlots", timeSlots);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        System.out.println(noCustomer.toString());

        HttpEntity<String> entity = new HttpEntity<>(noCustomer.toString(), headers);
        assertThat(this.restTemplate.postForEntity(BASE_TEST_URL + ":" + port + STYLIST_BOOKING_PATH, entity,
                Booking.class).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldShouldReturn400WhenThereAreNotTimeSlots() throws Exception {
        final JSONObject customer = new JSONObject();
        customer.put("name", "Name");

        final JSONObject noCustomer = new JSONObject();
        noCustomer.put("customer", customer);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        System.out.println(noCustomer.toString());

        HttpEntity<String> entity = new HttpEntity<>(noCustomer.toString(), headers);
        assertThat(this.restTemplate.postForEntity(BASE_TEST_URL + ":" + port + STYLIST_BOOKING_PATH, entity,
                Booking.class).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
