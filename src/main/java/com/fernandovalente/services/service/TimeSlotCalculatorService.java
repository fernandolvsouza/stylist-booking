package com.fernandovalente.services.service;

import com.fernandovalente.services.model.TimeSlot;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotCalculatorService {
    public static List<TimeSlot> calculateAllSpotsInBetween(LocalDate fromInclusive, LocalDate toInclusive) {
        if (toInclusive.isBefore(fromInclusive)) {
            throw new IllegalArgumentException("toInclusive should be greater or equal to fromInclusive");
        }

        int days = Period.between(fromInclusive, toInclusive).getDays() + 1;
        List<TimeSlot> timeSlots = new ArrayList<>(TimeSlot.MAX_TIME_SLOT_PER_DAY * days);

        for(int index = 0; index < days; index ++) {
            for(int daySlot = 0; daySlot < TimeSlot.MAX_TIME_SLOT_PER_DAY; daySlot ++) {
                timeSlots.add(new TimeSlot(daySlot, fromInclusive.plusDays(index)));
            }
        }
        return timeSlots;
    }
}
