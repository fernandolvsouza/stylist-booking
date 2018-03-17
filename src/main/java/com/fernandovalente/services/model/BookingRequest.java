package com.fernandovalente.services.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class BookingRequest {
    private Set<TimeSlot> timeSlots;
    private Customer customer;

    @JsonCreator
    public BookingRequest(@NonNull @NotEmpty Set<TimeSlot> timeSlots, @NonNull Customer customer) {
        this.timeSlots = timeSlots;
        this.customer = customer;
    }

    @NonNull
    public Customer getCustomer() {
        return customer;
    }

    @NonNull @NotEmpty
    public Set<TimeSlot> getTimeSlots() {
        return timeSlots;
    }
}
