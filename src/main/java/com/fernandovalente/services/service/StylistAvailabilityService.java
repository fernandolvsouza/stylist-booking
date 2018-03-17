package com.fernandovalente.services.service;

import com.fernandovalente.services.model.Stylist;
import com.fernandovalente.services.model.TimeSlot;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.Set;

@Component
public class StylistAvailabilityService {
    public Stylist findAvailableStylist(Set<TimeSlot> timeSlots) {
        return new Stylist("chosen stylist");
    }
}
