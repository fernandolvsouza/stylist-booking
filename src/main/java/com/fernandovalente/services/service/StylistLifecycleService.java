package com.fernandovalente.services.service;

import com.fernandovalente.services.dto.StylistOnBoardRequest;
import com.fernandovalente.services.exception.EntityNotFoundException;
import com.fernandovalente.services.model.Stylist;
import com.fernandovalente.services.model.StylistState;
import com.fernandovalente.services.repository.StylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Service that manages stylist life cycle
 */
@Component
public class StylistLifecycleService {
    @Autowired
    private StylistRepository stylistRepository;

    @Autowired
    private StylistStateMachine stylistStateMachine;

    /**
     * Creates new {@link Stylist} on the teh api
     */
    public Stylist onboard(@Valid StylistOnBoardRequest stylist) {
        return stylistRepository.save(new Stylist(stylist.getName(), StylistState.ROOKIE));
    }

    /**
     * Puts {@link Stylist} on ready mode
     */
    public Stylist notifyReady(@NotNull Long stylistId) {
        return saveState(stylistId, StylistState.READY);
    }

    /**
     * Puts {@link Stylist} on sick mode
     */
    public Stylist notifySick(@NotNull Long stylistId) {
        return saveState(stylistId, StylistState.SICK);
    }

    /**
     * Puts {@link Stylist} on holiday mode
     */
    public Stylist notifyHoliday(@NotNull Long stylistId) {
        return saveState(stylistId, StylistState.HOLIDAY);
    }

    /**
     * Puts {@link Stylist} on offboarded mode
     */
    public Stylist notifyOffboarding(@NotNull Long stylistId) {
        return saveState(stylistId, StylistState.OFFBOARDED);
    }

    private Stylist saveState(@NotNull Long stylistId, StylistState targetState) {
        Optional<Stylist> maybeStylist = stylistRepository.findById(stylistId);

        if (!maybeStylist.isPresent()) {
            throw new EntityNotFoundException("Stylist with id " + stylistId + " does not exist");
        }
        Stylist stylist = maybeStylist.get();

        if (stylist.getState().equals(targetState)) {
            throw new IllegalStateException("Stylist with id " + stylistId + " is already on state " + stylist.getState());
        }

        stylistStateMachine.validate(stylist, targetState);
        stylist.setState(targetState);
        return stylistRepository.save(stylist);
    }
}
