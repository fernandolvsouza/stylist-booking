package com.fernandovalente.services.helper;

import com.fernandovalente.services.dto.StylistOnBoardRequest;
import com.fernandovalente.services.model.Stylist;
import com.fernandovalente.services.model.StylistState;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

public class StylistLifeCycleTestHelper {
    private static final String BASE_TEST_URL = "http://localhost";
    private static final String STYLIST_LIFECYCLE_PATH = "/api/v1/stylist";
    private static final String STYLIST_READY_PATH = "/ready";
    private static final String STYLIST_SICK_PATH = "/sick";
    private static final String STYLIST_HOLIDAY_PATH = "/holiday";
    private static final String STYLIST_OFFBOARDING_PATH = "/offboarding";
    private TestRestTemplate restTemplate;
    private int port;

    public StylistLifeCycleTestHelper(int port, TestRestTemplate restTemplate) {
        this.port = port;
        this.restTemplate = restTemplate;
    }

    public Stylist createStylist(String name) {
        StylistOnBoardRequest stylist = new StylistOnBoardRequest(name);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<StylistOnBoardRequest> entity = new HttpEntity<>(stylist, headers);
        Stylist actualStylist = this.restTemplate.postForEntity(BASE_TEST_URL + ":" + port + STYLIST_LIFECYCLE_PATH,
                entity, Stylist.class).getBody();

        assertThat(actualStylist.getName()).isEqualTo(stylist.getName());
        assertThat(actualStylist.getId()).isNotNull();
        return actualStylist;
    }

    public Stylist changeStylistState(Stylist stylist, StylistState targetState) {
        String stateChangePath;
        switch (targetState) {
            case READY:
                stateChangePath = STYLIST_READY_PATH;
                break;
            case SICK:
                stateChangePath = STYLIST_SICK_PATH;
                break;
            case HOLIDAY:
                stateChangePath = STYLIST_HOLIDAY_PATH;
                break;
            case OFFBOARDED:
                stateChangePath = STYLIST_OFFBOARDING_PATH;
                break;
            default:
                throw new IllegalStateException("invalid target state");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Stylist> entity = new HttpEntity<>(stylist, headers);
        Stylist actualStylist = this.restTemplate.postForEntity(getStylistStateChangeUrl(stylist, stateChangePath),
                entity, Stylist.class).getBody();

        assertThat(actualStylist.getName()).isEqualTo(stylist.getName());
        assertThat(actualStylist.getState()).isEqualTo(targetState);
        assertThat(actualStylist.getId()).isEqualTo(stylist.getId());
        return actualStylist;
    }

    private String getStylistStateChangeUrl(Stylist stylist, String statePath) {
        return BASE_TEST_URL + ":" + port + STYLIST_LIFECYCLE_PATH + "/" + stylist.getId() + statePath;
    }
}
