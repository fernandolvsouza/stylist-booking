package com.fernandovalente.services.rest;

import com.fernandovalente.services.model.*;
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
    public void shouldCreateBookingAndReturnIt() {
        final TimeSlot expetedTimeSlot = new TimeSlot(0, LocalDate.now());
        final Customer expectedCustomer = new Customer("Customer 1");
        final BookingRequest bookingRequest = new BookingRequest(expetedTimeSlot, expectedCustomer);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BookingRequest> entity = new HttpEntity<>(bookingRequest, headers);
        Booking actualBooking = this.restTemplate.postForEntity(BASE_TEST_URL + ":" + port + STYLIST_BOOKING_PATH, entity,
                Booking.class).getBody();
        assertThat(actualBooking.getCustomer()).isEqualToComparingFieldByFieldRecursively(expectedCustomer);
        assertThat(actualBooking.getTimeSlot()).isEqualToComparingFieldByFieldRecursively(expetedTimeSlot);

    }

    @Test
    public void shouldShouldReturn400WhenThereIsNoCostumer() throws Exception {
        final JSONObject timeSlot = new JSONObject();
        timeSlot.put("daySlot", 0);
        timeSlot.put("day", "2018-03-17");

        final JSONObject noCustomer = new JSONObject();
        noCustomer.put("timeSlot", timeSlot);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        System.out.println(noCustomer.toString());

        HttpEntity<String> entity = new HttpEntity<>(noCustomer.toString(), headers);
        assertThat(this.restTemplate.postForEntity(BASE_TEST_URL + ":" + port + STYLIST_BOOKING_PATH, entity,
                Booking.class).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldShouldReturn400WhenThereAreNotTimeSlot() throws Exception {
        final JSONObject customer = new JSONObject();
        customer.put("name", "Name");

        final JSONObject noTimeSlot = new JSONObject();
        noTimeSlot.put("customer", customer);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        System.out.println(noTimeSlot.toString());

        HttpEntity<String> entity = new HttpEntity<>(noTimeSlot.toString(), headers);
        assertThat(this.restTemplate.postForEntity(BASE_TEST_URL + ":" + port + STYLIST_BOOKING_PATH, entity,
                Booking.class).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
