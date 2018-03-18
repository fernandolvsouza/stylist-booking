package com.fernandovalente.services.controller;

import com.fernandovalente.services.helper.StylistLifeCycleTestHelper;
import com.fernandovalente.services.model.Stylist;
import com.fernandovalente.services.model.StylistState;
import org.junit.Before;
import org.junit.ComparisonFailure;
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
    private static final String STYLIST_LIFECYCLE_PATH = "/api/v1/stylist";
    private static final String STYLIST_READY_PATH = "/ready";
    private static final String STYLIST_SICK_PATH = "/sick";
    private static final String STYLIST_HOLIDAY_PATH = "/holiday";
    private static final String STYLIST_OFFBOARDING_PATH = "/offboarding";


    private StylistLifeCycleTestHelper stylistLifeCycleTestHelper;

    @Before
    public void before() {
        stylistLifeCycleTestHelper = new StylistLifeCycleTestHelper(port, restTemplate);
    }

    @Test
    public void shouldCreateBookingAndReturnStateAsRookie() {
        Stylist actualStylist = stylistLifeCycleTestHelper.createStylist("Customer 1");
        assertThat(actualStylist.getState()).isEqualTo(StylistState.ROOKIE);
    }

    @Test
    public void shouldMoveStateMachineProperly() {
        Stylist stylist = stylistLifeCycleTestHelper.createStylist("Customer 2");
        testStateChange(stylist,  StylistState.READY, STYLIST_READY_PATH);
        testStateChange(stylist, StylistState.SICK, STYLIST_SICK_PATH);
        testStateChange(stylist, StylistState.READY, STYLIST_READY_PATH);
        testStateChange(stylist, StylistState.HOLIDAY, STYLIST_HOLIDAY_PATH);
        testStateChange(stylist, StylistState.READY, STYLIST_READY_PATH);
        testStateChange(stylist, StylistState.OFFBOARDED, STYLIST_OFFBOARDING_PATH);
    }

    @Test(expected = ComparisonFailure.class)
    public void shouldFailOnSickToHoliday() {
        Stylist stylist = stylistLifeCycleTestHelper.createStylist("Customer 3");
        testStateChange(stylist,  StylistState.READY, STYLIST_READY_PATH);
        testStateChange(stylist, StylistState.SICK, STYLIST_SICK_PATH);
        testStateChange(stylist, StylistState.HOLIDAY, STYLIST_HOLIDAY_PATH);
    }

    @Test(expected = ComparisonFailure.class)
    public void shouldFailOnRookieToOffboarding() {
        Stylist stylist = stylistLifeCycleTestHelper.createStylist("Customer 4");
        testStateChange(stylist, StylistState.OFFBOARDED, STYLIST_OFFBOARDING_PATH);
    }

    private void testStateChange(Stylist stylist, StylistState targetState, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Stylist> entity = new HttpEntity<>(stylist, headers);
        Stylist actualStylist = this.restTemplate.postForEntity(getStylistStateChangeUrl(stylist, path),
                entity, Stylist.class).getBody();

        assertThat(actualStylist.getName()).isEqualTo(stylist.getName());
        assertThat(actualStylist.getState()).isEqualTo(targetState);
        assertThat(actualStylist.getId()).isEqualTo(stylist.getId());
    }

    private String getStylistStateChangeUrl(Stylist stylist, String statePath) {
        return BASE_TEST_URL + ":" + port + STYLIST_LIFECYCLE_PATH + "/" + stylist.getId() + statePath;
    }
}
