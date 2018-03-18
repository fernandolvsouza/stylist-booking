package com.fernandovalente.services.helper;

import com.fernandovalente.services.model.Customer;
import com.fernandovalente.services.model.Stylist;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

public class StylistLifeCycleTestHelper {
    private static final String BASE_TEST_URL = "http://localhost";
    private static final String STYLIST_LIFECYCLE_PATH = "/api/v1/stylist-lifecycle";
    private static final String ONBOARDING_PATH = "/onboarding";
    private TestRestTemplate restTemplate;
    private int port;

    public StylistLifeCycleTestHelper(int port, TestRestTemplate restTemplate) {
        this.port = port;
        this.restTemplate = restTemplate;
    }

    public Stylist createStylist(String name) {
        Stylist stylist = new Stylist(name);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Stylist> entity = new HttpEntity<>(stylist, headers);
        Stylist actualStylist = this.restTemplate.postForEntity(BASE_TEST_URL + ":" + port + STYLIST_LIFECYCLE_PATH
                + ONBOARDING_PATH, entity, Stylist.class).getBody();

        assertThat(actualStylist.getName()).isEqualTo(stylist.getName());
        assertThat(actualStylist.getId()).isNotNull();
        return actualStylist;
    }
}
