package com.fernandovalente.services.controller;

import com.fernandovalente.services.model.Stylist;
import com.fernandovalente.services.service.StylistLifecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/api/v1/stylist-lifecycle")
public class StylistLifecycleController {

    @Autowired
    StylistLifecycleService service;

    @RequestMapping(method = RequestMethod.POST, path = "/onboarding")
    @ResponseBody
    public Stylist onboarding(@RequestBody @Valid Stylist stylist) {
        return service.onboard(stylist);
    }
}
