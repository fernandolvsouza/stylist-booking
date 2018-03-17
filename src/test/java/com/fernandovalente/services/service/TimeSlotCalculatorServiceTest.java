package com.fernandovalente.services.service;

import com.fernandovalente.services.model.TimeSlot;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeSlotCalculatorServiceTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenFromIsBeforeThanTo() {
        LocalDate from  = LocalDate.now();
        TimeSlotCalculatorService.calculateAllSpotsInBetween(from, from.minus(1, ChronoUnit.DAYS));
    }

    @Test
    public void shouldReturn16TimeSlotsWhenFromAndAfterAreTheSame() {
        LocalDate from  = LocalDate.now();
        List timeSlots = TimeSlotCalculatorService.calculateAllSpotsInBetween(from, from);
        assertThat(timeSlots).hasSize(TimeSlot.MAX_TIME_SLOT_PER_DAY);
    }

    @Test
    public void shouldReturnDaysMultipliedBy16TimeSlots() {
        int days = 10;
        LocalDate from  = LocalDate.now();
        List timeSlots = TimeSlotCalculatorService.calculateAllSpotsInBetween(from, from.plusDays(days-1));
        assertThat(timeSlots).hasSize(TimeSlot.MAX_TIME_SLOT_PER_DAY * days);
    }

    @Test
    public void should16TimeSlotsPerDay() {
        int days = 2;
        LocalDate from = LocalDate.now();
        LocalDate to = from.plusDays(days - 1);
        List<TimeSlot> timeSlots = TimeSlotCalculatorService.calculateAllSpotsInBetween(from, to);

        for(int index = 0; index < TimeSlot.MAX_TIME_SLOT_PER_DAY; index ++) {
            assertThat(timeSlots.get(index).getDaySlot()).isEqualTo(index);
            assertThat(timeSlots.get(index).getDay()).isEqualTo(from);
        }

        for(int index = TimeSlot.MAX_TIME_SLOT_PER_DAY; index < TimeSlot.MAX_TIME_SLOT_PER_DAY * 2; index ++) {
            assertThat(timeSlots.get(index).getDaySlot()).isEqualTo(index - TimeSlot.MAX_TIME_SLOT_PER_DAY);
            assertThat(timeSlots.get(index).getDay()).isEqualTo(to);
        }
    }
}
