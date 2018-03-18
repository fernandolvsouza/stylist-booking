package com.fernandovalente.services.controller;

import com.fernandovalente.services.dto.BookingRequest;
import com.fernandovalente.services.helper.CustomerTestHelper;
import com.fernandovalente.services.helper.StylistLifeCycleTestHelper;
import com.fernandovalente.services.model.*;
import org.json.JSONObject;
import org.junit.Before;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class StylistBookingControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_TEST_URL = "http://localhost";
    private static final String STYLIST_BOOKING_PATH = "/api/v1/stylist-booking";

    private CustomerTestHelper customerTestHelper;

    @Before
    public void before() {
        customerTestHelper = new CustomerTestHelper(port, restTemplate);
        StylistLifeCycleTestHelper stylistLifeCycleTestHelper = new StylistLifeCycleTestHelper(port, restTemplate);
        stylistLifeCycleTestHelper.createStylist("Stylist name");
    }

    @Test
    public void shouldCreateBookingAndReturnIt() {
        final TimeSlot expectedTimeSlot = new TimeSlot(0, LocalDate.now());
        final Customer createdCustomer = customerTestHelper.createCustomer("Customer 1");
        final BookingRequest bookingRequest = new BookingRequest(expectedTimeSlot, createdCustomer.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BookingRequest> entity = new HttpEntity<>(bookingRequest, headers);
        Booking actualBooking = this.restTemplate.postForEntity(BASE_TEST_URL + ":" + port + STYLIST_BOOKING_PATH,
                entity, Booking.class).getBody();
        assertThat(actualBooking.getCustomer()).isEqualToComparingFieldByFieldRecursively(createdCustomer);
        assertThat(actualBooking.getTimeSlot()).isEqualToComparingFieldByFieldRecursively(expectedTimeSlot);
    }

    @Test
    public void shouldReturn400WhenThereIsNoCostumer() throws Exception {
        final JSONObject timeSlot = new JSONObject();
        timeSlot.put("daySlot", 0);
        timeSlot.put("day", "2018-03-17");

        final JSONObject noCustomer = new JSONObject();
        noCustomer.put("timeSlot", timeSlot);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(noCustomer.toString(), headers);
        assertThat(this.restTemplate.postForEntity(BASE_TEST_URL + ":" + port + STYLIST_BOOKING_PATH, entity,
                Booking.class).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldReturn400WhenThereAreNotTimeSlot() throws Exception {
        final Customer createdCustomer = customerTestHelper.createCustomer("Customer 2");
        final JSONObject noTimeSlot = new JSONObject();
        noTimeSlot.put("customerId", createdCustomer.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        System.out.println(noTimeSlot.toString());

        HttpEntity<String> entity = new HttpEntity<>(noTimeSlot.toString(), headers);
        assertThat(this.restTemplate.postForEntity(BASE_TEST_URL + ":" + port + STYLIST_BOOKING_PATH, entity,
                Booking.class).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
