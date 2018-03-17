package com.fernandovalente.services.rest;

import com.fernandovalente.services.model.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.asm.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import sun.jvm.hotspot.debugger.Page;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class StylistAvailabilityControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_TEST_URL = "http://localhost";
    private static final String STYLIST_AVAILABILITY_PATH = "/api/v1/stylist-availability";
    private static final String STYLIST_BOOKING_PATH = "/api/v1/stylist-booking";

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
        assertThat(entity.getBody()).hasSize(16);
    }

    @Test
    public void shouldReturn32TimeSlotsForTwoDay() {
        ResponseEntity<TimeSlot[]> entity = this.restTemplate.getForEntity(BASE_TEST_URL + ":" + port +
                STYLIST_AVAILABILITY_PATH + "?fromInclusive=2018-03-19&toInclusive=2018-03-20", TimeSlot[].class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).hasSize(32);
    }

    @Test
    public void shouldReturn8SlotsWhen8AreBooked() throws JSONException {
        String today = "2018-03-17";
        for (int index = 0; index < 8; index++) {
            final JSONObject timeSlot = new JSONObject();
            timeSlot.put("daySlot", index);
            timeSlot.put("day", today);

            final JSONObject customer = new JSONObject();
            customer.put("name", "Name");

            final JSONObject booking = new JSONObject();
            booking.put("customer", customer);
            booking.put("timeSlot", timeSlot);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(booking.toString(), headers);
            ResponseEntity responseEntity = this.restTemplate.postForEntity(BASE_TEST_URL + ":" + port +
                    STYLIST_BOOKING_PATH, entity, Booking.class);
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        ResponseEntity<TimeSlot[]> entity = this.restTemplate.getForEntity(BASE_TEST_URL + ":" + port +
                STYLIST_AVAILABILITY_PATH + "?fromInclusive="+ today + "&toInclusive="+ today, TimeSlot[].class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).hasSize(8);
    }
}
