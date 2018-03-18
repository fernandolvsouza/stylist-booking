package com.fernandovalente.services.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BookingRequest {
    private TimeSlot timeSlot;
    private Long customerId;

    @JsonCreator
    public BookingRequest(@NotNull @NotEmpty @JsonProperty("timeSlot") TimeSlot timeSlot,
                          @NotNull @JsonProperty("customerId") Long customerId) {
        this.timeSlot = timeSlot;
        this.customerId = customerId;
    }

    @NotNull
    public Long getCustomerId() {
        return customerId;
    }

    @NotNull
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    @Override
    public String toString() {
        return "BookingRequest{" +
                "timeSlot=" + timeSlot +
                ", customer=" + customerId +
                '}';
    }
}
