package com.fernandovalente.services.controller;

import com.fernandovalente.services.model.Booking;
import com.fernandovalente.services.model.BookingRequest;
import com.fernandovalente.services.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/api/v1/stylist-booking")
public class StylistBookingController {

    @Autowired
    BookingService service;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Booking bookStylist(@RequestBody @Valid BookingRequest bookingRequest) {
        return service.book(bookingRequest.getCustomer(), bookingRequest.getTimeSlots());
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Booking> getStylistBookingList() {
        return service.getAllStylistBooking();
    }
}
