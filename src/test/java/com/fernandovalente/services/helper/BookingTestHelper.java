package com.fernandovalente.services.helper;

import com.fernandovalente.services.model.Booking;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class BookingTestHelper {
    private static final String BASE_TEST_URL = "http://localhost";
    private static final String STYLIST_BOOKING_PATH = "/api/v1/stylist-booking";
    private TestRestTemplate restTemplate;
    private int port;

    public BookingTestHelper(int port, TestRestTemplate restTemplate) {
        this.port = port;
        this.restTemplate = restTemplate;
    }

    public void bookSlot(LocalDate day, Integer slot, Long customerId) throws JSONException {
        final JSONObject booking = buildBookingRequestJson(day, slot, customerId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(booking.toString(), headers);
        ResponseEntity responseEntity = this.restTemplate.postForEntity(BASE_TEST_URL + ":" + port +
                STYLIST_BOOKING_PATH, entity, Booking.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    public JSONObject buildBookingRequestJson(LocalDate day, Integer slot, Long customerId) throws JSONException {
        final JSONObject timeSlot = new JSONObject();
        timeSlot.put("daySlot", slot);
        timeSlot.put("day", day.toString());

        final JSONObject booking = new JSONObject();
        booking.put("customerId", customerId);
        booking.put("timeSlot", timeSlot);
        return booking;
    }
}
