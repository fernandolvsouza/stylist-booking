package com.fernandovalente.services.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import java.util.Set;

public class Booking {

    private Stylist stylist;
    private Set<TimeSlot> timeSlots;
    private Customer customer;

    @JsonCreator
    public Booking(@NonNull @JsonProperty("stylist") Stylist stylist, @NonNull @JsonProperty("timeSlots")
            Set<TimeSlot> timeSlots, @NonNull @JsonProperty("customer") Customer customer) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        if (!stylist.equals(booking.stylist)) return false;
        if (!timeSlots.equals(booking.timeSlots)) return false;
        return customer.equals(booking.customer);
    }

    @Override
    public int hashCode() {
        int result = stylist.hashCode();
        result = 31 * result + timeSlots.hashCode();
        result = 31 * result + customer.hashCode();
        return result;
    }
}
