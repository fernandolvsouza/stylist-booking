package com.fernandovalente.services.controller;

import com.fernandovalente.services.dto.StylistOnBoardRequest;
import com.fernandovalente.services.model.Stylist;
import com.fernandovalente.services.service.StylistLifecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller responsable for stylists lifecycle
 */
@Controller
@RequestMapping(path = "/api/v1/stylist")
public class StylistLifecycleController {

    @Autowired
    StylistLifecycleService service;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Stylist onboarding(@RequestBody @Valid StylistOnBoardRequest stylist) {
        return service.onboard(stylist);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{stylistId}/ready")
    @ResponseBody
    public Stylist ready(@PathVariable("stylistId") long stylistId) {
        return service.notifyReady(stylistId);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{stylistId}/sick")
    @ResponseBody
    public Stylist sick(@PathVariable("stylistId") long stylistId) {
        return service.notifySick(stylistId);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{stylistId}/holiday")
    @ResponseBody
    public Stylist holiday(@PathVariable("stylistId") long stylistId) {
        return service.notifyHoliday(stylistId);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{stylistId}/offboarding")
    @ResponseBody
    public Stylist offboarding(@PathVariable("stylistId") long stylistId) {
        return service.notifyOffboarding(stylistId);
    }
}
