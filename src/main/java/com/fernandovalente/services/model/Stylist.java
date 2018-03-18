package com.fernandovalente.services.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class Stylist {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "stylist")
    private List<Booking> bookings;

    // no args constructor required by JPA SPEC
    protected Stylist() {
    }

    @JsonCreator
    public Stylist(@NonNull @JsonProperty("name") String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    @NonNull
    @JsonIgnore
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stylist stylist = (Stylist) o;

        return name.equals(stylist.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Stylist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
