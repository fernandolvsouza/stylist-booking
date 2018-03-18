package com.fernandovalente.services.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class BatchBookingResult {
    private List<BookingRequest> failedBookingRequests;
    private List<Booking> succeedBookingRequests;

    @JsonCreator
    public BatchBookingResult(@JsonProperty("failedBookingRequests") List<BookingRequest> failedBookingRequests,
                              @JsonProperty("succeedBookingRequests")List<Booking> succeedBookingRequests) {
        this.failedBookingRequests = failedBookingRequests;
        this.succeedBookingRequests = succeedBookingRequests;
    }

    @NotNull
    public List<BookingRequest> getFailedBookingRequests() {
        return failedBookingRequests;
    }

    @NotNull
    public List<Booking> getSucceedBookingRequests() {
        return succeedBookingRequests;
    }

    public void addFailed(BookingRequest request) {
        failedBookingRequests.add(request);
    }

    public void addSucceed(Booking request) {
        succeedBookingRequests.add(request);
    }

    @Override
    public String toString() {
        return "BatchBookingResult{" +
                "failedBookingRequests=" + failedBookingRequests +
                ", succeedBookingRequests=" + succeedBookingRequests +
                '}';
    }
}
