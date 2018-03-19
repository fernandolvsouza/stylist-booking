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
        stylistLifeCycleTestHelper.changeStylistState(stylist, StylistState.READY);
        stylistLifeCycleTestHelper.changeStylistState(stylist, StylistState.SICK);
        stylistLifeCycleTestHelper.changeStylistState(stylist, StylistState.READY);
        stylistLifeCycleTestHelper.changeStylistState(stylist, StylistState.HOLIDAY);
        stylistLifeCycleTestHelper.changeStylistState(stylist, StylistState.READY);
        stylistLifeCycleTestHelper.changeStylistState(stylist, StylistState.OFFBOARDED);
    }

    @Test(expected = ComparisonFailure.class)
    public void shouldFailOnSickToHoliday() {
        Stylist stylist = stylistLifeCycleTestHelper.createStylist("Customer 3");
        stylistLifeCycleTestHelper.changeStylistState(stylist, StylistState.READY);
        stylistLifeCycleTestHelper.changeStylistState(stylist, StylistState.SICK);
        stylistLifeCycleTestHelper.changeStylistState(stylist, StylistState.HOLIDAY);
    }

    @Test(expected = ComparisonFailure.class)
    public void shouldFailOnRookieToOffboarding() {
        Stylist stylist = stylistLifeCycleTestHelper.createStylist("Customer 4");
        stylistLifeCycleTestHelper.changeStylistState(stylist, StylistState.OFFBOARDED);
    }
}
