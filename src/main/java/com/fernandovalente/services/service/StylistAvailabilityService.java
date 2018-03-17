package com.fernandovalente.services.service;

import com.fernandovalente.services.model.Stylist;
import com.fernandovalente.services.model.TimeSlot;
import com.fernandovalente.services.repository.StylistRepository;
import com.fernandovalente.services.repository.TimeSlotRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StylistAvailabilityService {

    private StylistRepository stylistRepository;
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    public StylistAvailabilityService(StylistRepository stylistRepository, TimeSlotRepository timeSlotRepository) {
        this.stylistRepository = stylistRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    public Stylist findAvailableStylist(TimeSlot timeSlot) {
        Stylist stylist = new Stylist("chosen stylist");
        return stylistRepository.save(stylist);
    }

    public List<TimeSlot> getAvailableTimeSlotsInBetweenDays(LocalDate fromInclusive, LocalDate fromExclusive) {
        List<TimeSlot> occupiedTimeSlots = Lists.newArrayList(
                timeSlotRepository.findByDayBetween(fromInclusive, fromExclusive)
        );
        // TODO refactor MxN to a better algorithm, use of sorting may be possible
        return TimeSlotCalculatorService.calculateAllSpotsInBetween(fromInclusive, fromExclusive)
                .stream()
                .filter((timeSlot) ->
                    !occupiedTimeSlots.stream().anyMatch(
                            (occupiedTimeSlot) -> occupiedTimeSlot.getDay().equals(timeSlot.getDay())
                            && occupiedTimeSlot.getDaySlot() == timeSlot.getDaySlot()
                    )
                ).collect(Collectors.toList());
    }
}
