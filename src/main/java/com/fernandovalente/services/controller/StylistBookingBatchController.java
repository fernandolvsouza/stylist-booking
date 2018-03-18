package com.fernandovalente.services.controller;

import com.fernandovalente.services.model.BatchBookingResult;
import com.fernandovalente.services.model.BookingRequest;
import com.fernandovalente.services.service.BatchBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/api/v1/stylist-booking-batch-task")
public class StylistBookingBatchController {

    @Autowired
    BatchBookingService service;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public BatchBookingResult bookStylistBatch(@RequestBody @Valid List<BookingRequest> bookingRequests) {
        return service.bookBatch(bookingRequests);
    }
}
