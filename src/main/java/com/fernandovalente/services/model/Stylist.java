package com.fernandovalente.services.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Stylist {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    // no args constructor required by JAP SPEC
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
}
