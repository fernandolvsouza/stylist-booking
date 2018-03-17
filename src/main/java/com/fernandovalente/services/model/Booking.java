package com.fernandovalente.services.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Stylist stylist;

    @OneToOne
    private TimeSlot timeSlot;

    @OneToOne
    private Customer customer;

    // no args constructor required by JAP SPEC
    protected Booking() {
    }

    @JsonCreator
    public Booking(@NonNull @JsonProperty("stylist") Stylist stylist, @NonNull @JsonProperty("timeSlot")
            TimeSlot timeSlot, @NonNull @JsonProperty("customer") Customer customer) {
        this.stylist = stylist;
        this.timeSlot = timeSlot;
        this.customer = customer;
    }

    @NonNull
    public Stylist getStylist() {
        return stylist;
    }

    @NonNull
    public TimeSlot getTimeSlot() {
        return timeSlot;
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

        if (!id.equals(booking.id)) return false;
        if (!stylist.equals(booking.stylist)) return false;
        if (!timeSlot.equals(booking.timeSlot)) return false;
        return customer.equals(booking.customer);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + stylist.hashCode();
        result = 31 * result + timeSlot.hashCode();
        result = 31 * result + customer.hashCode();
        return result;
    }
}
