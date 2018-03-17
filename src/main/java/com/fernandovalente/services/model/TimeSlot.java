package com.fernandovalente.services.model;

import org.springframework.lang.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

public class TimeSlot {

    private int daySlot;
    private LocalDate day;

    public TimeSlot(@Min(0) @Max(15) int daySlot, @NonNull LocalDate day) {
        this.daySlot = daySlot;
        this.day = day;
    }

    @Min(0)
    @Max(15)
    @NonNull
    public int getDaySlot() {
        return daySlot;
    }

    @NonNull
    public LocalDate getDay() {
        return day;
    }
}
