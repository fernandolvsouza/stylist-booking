package com.fernandovalente.services.helper;

import com.fernandovalente.services.model.Customer;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerTestHelper {
    private static final String BASE_TEST_URL = "http://localhost";
    private static final String CUSTOMER_PATH = "/api/v1/customer";
    private TestRestTemplate restTemplate;
    private int port;

    public CustomerTestHelper(int port, TestRestTemplate restTemplate) {
        this.port = port;
        this.restTemplate = restTemplate;
    }

    public Customer createCustomer(String name) {
        Customer customer = new Customer(name);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Customer> entity = new HttpEntity<>(customer, headers);
        Customer actualCustomer = this.restTemplate.postForEntity(BASE_TEST_URL + ":" + port + CUSTOMER_PATH, entity,
                Customer.class).getBody();
        assertThat(actualCustomer.getName()).isEqualTo(customer.getName());
        assertThat(actualCustomer.getId()).isNotNull();
        return actualCustomer;
    }
}
