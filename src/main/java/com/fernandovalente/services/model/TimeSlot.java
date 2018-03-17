package com.fernandovalente.services.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

public class TimeSlot {

    private int daySlot;
    private LocalDate day;

    @JsonCreator
    public TimeSlot(@Min(0) @Max(15) @JsonProperty("daySlot") Integer daySlot, @NonNull @JsonProperty("day") LocalDate day) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeSlot timeSlot = (TimeSlot) o;

        if (daySlot != timeSlot.daySlot) return false;
        return day.equals(timeSlot.day);
    }

    @Override
    public int hashCode() {
        int result = daySlot;
        result = 31 * result + day.hashCode();
        return result;
    }
}
