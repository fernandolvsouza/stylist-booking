package com.fernandovalente.services.controller;

import com.fernandovalente.services.model.Stylist;
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
public class StylistLifecycleControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_TEST_URL = "http://localhost";
    private static final String STYLIST_LIFECYCLE_PATH = "/api/v1/stylist-lifecycle";
    private static final String ONBOARDING_PATH = "/onboarding";

    @Test
    public void shouldCreateBookingAndReturnIt() {
        Stylist stylist = new Stylist("Customer 1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Stylist> entity = new HttpEntity<>(stylist, headers);
        Stylist actualStylist = this.restTemplate.postForEntity(BASE_TEST_URL + ":" + port + STYLIST_LIFECYCLE_PATH
                + ONBOARDING_PATH, entity, Stylist.class).getBody();

        assertThat(actualStylist.getName()).isEqualTo(stylist.getName());
        assertThat(actualStylist.getId()).isNotNull();
    }
}
