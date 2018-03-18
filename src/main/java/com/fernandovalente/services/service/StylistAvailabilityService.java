package com.fernandovalente.services.service;

import com.fernandovalente.services.model.Stylist;
import com.fernandovalente.services.model.TimeSlot;
import com.fernandovalente.services.repository.StylistRepository;
import com.fernandovalente.services.repository.TimeSlotRepository;
import com.google.common.collect.Lists;
import org.omg.SendingContext.RunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional(readOnly = true)
public class StylistAvailabilityService {

    private StylistRepository stylistRepository;
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    public StylistAvailabilityService(StylistRepository stylistRepository, TimeSlotRepository timeSlotRepository) {
        this.stylistRepository = stylistRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    public Stylist findAvailableStylist(TimeSlot timeSlot) {
        List<Stylist> availableStylists = stylistRepository.findStylistsWithoutTimeSlotBooked(timeSlot.getDay(),
                timeSlot.getDaySlot());
        if (availableStylists.isEmpty()) {
            throw new IllegalStateException("Could not found available stylist for slot" + timeSlot);
        }
        return availableStylists.get(0);
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
