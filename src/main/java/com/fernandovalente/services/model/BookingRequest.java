package com.fernandovalente.services.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BookingRequest {
    private TimeSlot timeSlot;
    private Customer customer;

    @JsonCreator
    public BookingRequest(@NotNull @NotEmpty @JsonProperty("timeSlot") TimeSlot timeSlot,
                          @NotNull @JsonProperty("customer") Customer customer) {
        this.timeSlot = timeSlot;
        this.customer = customer;
    }

    @NotNull
    public Customer getCustomer() {
        return customer;
    }

    @NotNull
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }
}
