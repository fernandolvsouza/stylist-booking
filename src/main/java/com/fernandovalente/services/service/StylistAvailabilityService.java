package com.fernandovalente.services.service;

import com.fernandovalente.services.model.Stylist;
import com.fernandovalente.services.model.TimeSlot;
import com.fernandovalente.services.repository.StylistRepository;
import com.fernandovalente.services.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsible for figuring out stylists availability
 */
@Component
@Transactional(readOnly = true)
public class StylistAvailabilityService {

    private StylistRepository stylistRepository;
    private TimeSlotRepository timeSlotRepository;

    /**
     * @param stylistRepository  stylist repository
     * @param timeSlotRepository time slots repository
     */
    @Autowired
    public StylistAvailabilityService(StylistRepository stylistRepository, TimeSlotRepository timeSlotRepository) {
        this.stylistRepository = stylistRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    /**
     * Find first stylist free for this time slot
     *
     * @param timeSlot time slot that stylist should be free
     * @return free {@link Stylist}
     */
    public Stylist findAvailableStylist(TimeSlot timeSlot) {
        List<Stylist> availableStylists = stylistRepository.findStylistsWithoutTimeSlotBooked(timeSlot.getDay(),
                timeSlot.getDaySlot());
        if (availableStylists.isEmpty()) {
            throw new IllegalStateException("Could not found available stylist for slot" + timeSlot);
        }
        return availableStylists.get(0);
    }


    /**
     * Get all available time slots between fromInclusive {@link LocalDate} and toInclusive {@link LocalDate}.
     * Takes in consideration if there at least one stylist available for the time slot
     *
     * @param fromInclusive {@link LocalDate} start day for search of slots
     * @param toInclusive   {@link LocalDate} end day inclusive for search of slots
     * @return free {@link TimeSlot}
     */
    public List<TimeSlot> getAvailableTimeSlotsInBetweenDays(LocalDate fromInclusive, LocalDate toInclusive) {
        List<Object[]> timeSlotsNoIds = timeSlotRepository.findTimeSlotsTotallyBooked(fromInclusive, toInclusive);
        List<TimeSlot> occupiedTimeSlots = timeSlotsNoIds
                .stream()
                .map((pair) -> new TimeSlot((Integer)pair[1], (LocalDate)pair[0]))
                .collect(Collectors.toList());

        // TODO refactor MxN to a better algorithm, use of sorting may be possible
        return TimeSlotCalculatorService.calculateAllSpotsInBetween(fromInclusive, toInclusive)
                .stream()
                .filter((timeSlot) ->
                        !occupiedTimeSlots.stream().anyMatch(
                                (occupiedTimeSlot) -> occupiedTimeSlot.getDay().equals(timeSlot.getDay())
                                        && occupiedTimeSlot.getDaySlot() == timeSlot.getDaySlot()
                        )
                ).collect(Collectors.toList());
    }
}
