package com.fernandovalente.services.controller;

import com.fernandovalente.services.model.Customer;
import com.fernandovalente.services.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/api/v1/customer")
public class CustomerController {

    @Autowired
    CustomerService service;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Customer register(@RequestBody @Valid Customer customer) {
        return service.register(customer);
    }
}
