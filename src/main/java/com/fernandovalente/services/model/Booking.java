package com.fernandovalente.services.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.NonNull;

import java.util.Set;

public class Booking {

    private Stylist stylist;
    private Set<TimeSlot> timeSlots;
    private Customer customer;

    @JsonCreator
    public Booking(@NonNull Stylist stylist, @NonNull Set<TimeSlot> timeSlots, @NonNull Customer customer) {
        this.stylist = stylist;
        this.timeSlots = timeSlots;
        this.customer = customer;
    }

    @NonNull
    public Stylist getStylist() {
        return stylist;
    }

    @NonNull
    public Set<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    @NonNull
    public Customer getCustomer() {
        return customer;
    }
}
