package com.fernandovalente.services.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

public class CustomerRegistrationRequest {

    private String name;

    @JsonCreator
    public CustomerRegistrationRequest(@NonNull @JsonProperty("name") String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
