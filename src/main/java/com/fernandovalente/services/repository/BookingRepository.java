package com.fernandovalente.services.repository;

import com.fernandovalente.services.model.Booking;

import java.util.List;

public interface BookingRepository {
    Booking create(Booking booking);
    List<Booking> getAll();
}
