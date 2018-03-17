package com.fernandovalente.services.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class BookingRequest {
    private Set<TimeSlot> timeSlots;
    private Customer customer;

    @JsonCreator
    public BookingRequest(@NotNull @NotEmpty @JsonProperty("timeSlots") Set<TimeSlot> timeSlots, @NotNull @JsonProperty("customer") Customer customer) {
        this.timeSlots = timeSlots;
        this.customer = customer;
    }

    @NotNull
    public Customer getCustomer() {
        return customer;
    }

    @NotNull @NotEmpty
    public Set<TimeSlot> getTimeSlots() {
        return timeSlots;
    }
}
