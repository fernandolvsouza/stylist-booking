package com.fernandovalente.services.service;

import com.fernandovalente.services.model.Stylist;
import com.fernandovalente.services.model.TimeSlot;
import com.fernandovalente.services.repository.StylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.Set;

@Component
public class StylistAvailabilityService {
    @Autowired
    private StylistRepository stylistRepository;

    public Stylist findAvailableStylist(Set<TimeSlot> timeSlots) {
        Stylist stylist = new Stylist("chosen stylist");
        return stylistRepository.save(stylist);
    }
}
