package com.fernandovalente.services.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.NonNull;

public class Stylist {
    private String name;

    @JsonCreator
    public Stylist(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
