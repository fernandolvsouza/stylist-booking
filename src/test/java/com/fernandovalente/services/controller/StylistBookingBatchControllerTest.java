package com.fernandovalente.services.controller;

import com.fernandovalente.services.helper.BookingTestHelper;
import com.fernandovalente.services.helper.CustomerTestHelper;
import com.fernandovalente.services.helper.StylistLifeCycleTestHelper;
import com.fernandovalente.services.model.BatchBookingResult;
import com.fernandovalente.services.model.Booking;
import com.fernandovalente.services.model.Customer;
import com.fernandovalente.services.model.Stylist;
import org.json.JSONArray;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class StylistBookingBatchControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_TEST_URL = "http://localhost";
    private static final String STYLIST_BOOKING_PATH = "/api/v1/stylist-booking-batch-task";

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
    public void shouldBookInBatchProperly() throws Exception {
        Customer customer = customerTestHelper.createCustomer("Customer 3");
        Stylist onlyStylist = stylistLifeCycleTestHelper.createStylist("Stylist 3");

        JSONArray requests = new JSONArray();
        for(int slot = 0; slot < 16; slot ++) {
            requests.put(
                    bookingTestHelper.buildBookingRequestJson(LocalDate.now(), slot, customer.getId())
            );
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requests.toString(), headers);
        ResponseEntity<BatchBookingResult> responseEntity = this.restTemplate.postForEntity(
                BASE_TEST_URL + ":" + port + STYLIST_BOOKING_PATH, entity, BatchBookingResult.class);
        List<Booking> succeedBooking = responseEntity.getBody().getSucceedBookingRequests();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getSucceedBookingRequests()).hasSize(16);
        assertThat(responseEntity.getBody().getFailedBookingRequests()).hasSize(0);
        for (Booking booking : succeedBooking) {
            assertThat(booking.getStylist()).isEqualToComparingFieldByFieldRecursively(onlyStylist);
        }

    }
}
