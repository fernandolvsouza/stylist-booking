package com.fernandovalente.services.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

public class Stylist {
    private String name;

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
