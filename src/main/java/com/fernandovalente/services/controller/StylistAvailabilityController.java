package com.fernandovalente.services.controller;

import com.fernandovalente.services.model.TimeSlot;
import com.fernandovalente.services.service.StylistAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping(path = "/api/v1/stylist-availability")
public class StylistAvailabilityController {

    @Autowired
    StylistAvailabilityService service;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<TimeSlot> getAvailableTimeSlots(@Valid @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                LocalDate fromInclusive,
                                                    @Valid @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                            LocalDate toInclusive) {
        return service.getAvailableTimeSlotsInBetweenDays(fromInclusive, toInclusive);
    }
}
