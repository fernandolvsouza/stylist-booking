package com.fernandovalente.services.service;

import com.fernandovalente.services.model.Stylist;
import com.fernandovalente.services.model.StylistState;
import org.springframework.stereotype.Component;

@Component
public class StylistStateMachine {
    public void validate(Stylist stylist, StylistState target) {
        boolean isValid = false;

        switch (stylist.getState()) {
            case ROOKIE:
                if (target.equals(StylistState.READY)) {
                    isValid = true;
                }
                break;
            case READY:
                if (target.equals(StylistState.SICK) || target.equals(StylistState.HOLIDAY)
                        || target.equals(StylistState.OFFBOARDED)) {
                    isValid = true;
                }
                break;
            case SICK:
                if (target.equals(StylistState.READY)) {
                    isValid = true;
                }
                break;
            case HOLIDAY:
                if (target.equals(StylistState.READY)) {
                    isValid = true;
                }
                break;
            case OFFBOARDED:
                break;

        }
        if (!isValid) {
            throw new IllegalStateException("Could not change state of stylist " + stylist.getId() + " from " +
                    stylist.getState() + " to " + target);
        }
    }
}
