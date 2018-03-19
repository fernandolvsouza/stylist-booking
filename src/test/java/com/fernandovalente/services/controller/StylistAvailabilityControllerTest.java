package com.fernandovalente.services.controller;

import com.fernandovalente.services.helper.BookingTestHelper;
import com.fernandovalente.services.helper.CustomerTestHelper;
import com.fernandovalente.services.helper.StylistLifeCycleTestHelper;
import com.fernandovalente.services.model.*;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class StylistAvailabilityControllerTest {
    private static final String BASE_TEST_URL = "http://localhost";

    private static final String STYLIST_AVAILABILITY_PATH = "/api/v1/stylist-availability";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private BookingTestHelper bookingTestHelper;
    private CustomerTestHelper customerTestHelper;
    private StylistLifeCycleTestHelper stylistLifeCycleTestHelper;

    @Before
    public void before() {
        bookingTestHelper = new BookingTestHelper(port, restTemplate);
        customerTestHelper = new CustomerTestHelper(port, restTemplate);
        stylistLifeCycleTestHelper = new StylistLifeCycleTestHelper(port, restTemplate);
    }

    @Test
    public void shouldReturn400WhenMissingFrom() {
        ResponseEntity entity = this.restTemplate.getForEntity(BASE_TEST_URL + ":" + port +
                STYLIST_AVAILABILITY_PATH + "?toInclusive=2018-03-19", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldReturn400WhenMissingTo() {
        ResponseEntity entity = this.restTemplate.getForEntity(BASE_TEST_URL + ":" + port +
                STYLIST_AVAILABILITY_PATH + "?fromInclusive=2018-03-19", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldReturn16TimeSlotsForOneDay() {
        ResponseEntity<TimeSlot[]> entity = this.restTemplate.getForEntity(BASE_TEST_URL + ":" + port +
                STYLIST_AVAILABILITY_PATH + "?fromInclusive=2018-03-19&toInclusive=2018-03-19", TimeSlot[].class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).hasSize(TimeSlot.MAX_TIME_SLOT_PER_DAY);
    }

    @Test
    public void shouldReturn32TimeSlotsForTwoDay() {
        ResponseEntity<TimeSlot[]> entity = this.restTemplate.getForEntity(BASE_TEST_URL + ":" + port +
                STYLIST_AVAILABILITY_PATH + "?fromInclusive=2018-03-19&toInclusive=2018-03-20", TimeSlot[].class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).hasSize(TimeSlot.MAX_TIME_SLOT_PER_DAY * 2);
    }

    @Test
    public void shouldReturnAvailableSlotsProperly() throws JSONException {
        final String today = "2018-03-17";
        Stylist stylist = stylistLifeCycleTestHelper.createStylist("first Stylist");
        stylistLifeCycleTestHelper.changeStylistState(stylist, StylistState.READY);
        Customer customer = customerTestHelper.createCustomer("Batch costumer");
        for (int slot = 0; slot < 8; slot++) {
            bookingTestHelper.bookSlot(LocalDate.parse(today), slot, customer.getId());
        }

        final ResponseEntity<TimeSlot[]> entity = this.restTemplate.getForEntity(BASE_TEST_URL + ":" + port +
                STYLIST_AVAILABILITY_PATH + "?fromInclusive=" + today + "&toInclusive=" + today, TimeSlot[].class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).hasSize(TimeSlot.MAX_TIME_SLOT_PER_DAY / 2);

        Stylist secondStylist = stylistLifeCycleTestHelper.createStylist("second Stylist");
        stylistLifeCycleTestHelper.changeStylistState(secondStylist, StylistState.READY);

        final ResponseEntity<TimeSlot[]> secondsEntity = this.restTemplate.getForEntity(BASE_TEST_URL + ":" + port +
                STYLIST_AVAILABILITY_PATH + "?fromInclusive=" + today + "&toInclusive=" + today, TimeSlot[].class);
        assertThat(secondsEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(secondsEntity.getBody()).hasSize(TimeSlot.MAX_TIME_SLOT_PER_DAY);

    }
}
