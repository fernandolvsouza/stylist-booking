package com.fernandovalente.services.service;

import com.fernandovalente.services.model.Stylist;
import com.fernandovalente.services.repository.StylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class StylistLifecycleService {
    @Autowired
    private StylistRepository stylistRepository;

    public Stylist onboard(@Valid Stylist stylist) {
        return stylistRepository.save(stylist);
    }
}
