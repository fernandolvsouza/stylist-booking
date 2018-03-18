package com.fernandovalente.services.controller;

import com.fernandovalente.services.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class CustomerControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_TEST_URL = "http://localhost";
    private static final String CUSTOMER_PATH = "/api/v1/customer";

    @Test
    public void shouldCreateBookingAndReturnIt() {
        Customer customer = new Customer("Customer 1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Customer> entity = new HttpEntity<>(customer, headers);
        Customer actualCustomer = this.restTemplate.postForEntity(BASE_TEST_URL + ":" + port + CUSTOMER_PATH, entity,
                Customer.class).getBody();
        System.out.println(actualCustomer);
        assertThat(actualCustomer.getName()).isEqualTo(customer.getName());
        assertThat(actualCustomer.getId()).isNotNull();
    }
}
