package com.fernandovalente.services.service;

import com.fernandovalente.services.dto.BatchBookingResult;
import com.fernandovalente.services.model.Booking;
import com.fernandovalente.services.dto.BookingRequest;
import com.fernandovalente.services.model.Stylist;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
/**
 * Service that deals with batch stylist calls bookings
 */
@Component
public class BatchBookingService {
    private static final Logger LOG = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    BookingService service;
    /**
     * Tries Books {@link List<BookingRequest>}
     * @param bookingRequests request for booking a call
     * @return {@link BatchBookingResult} result with failed request and succeeded ones
     */
    public BatchBookingResult bookBatch(List<BookingRequest> bookingRequests) {
        return bookingRequests.stream().map((bookingRequest) -> {
            Booking booking = null;
            Boolean succeed;
            try {
                booking = service.book(bookingRequest.getCustomerId(), bookingRequest.getTimeSlot());
                LOG.info("Call with stylist successfully booked :: {}", booking);
                succeed = true;
            } catch (Exception exception) {
                LOG.error("Could not book call with stylist", exception);
                succeed = false;
            }
            return Triplet.with(bookingRequest, succeed, booking);

        }).collect(
                Collector.of(
                        () -> new BatchBookingResult(new ArrayList<>(), new ArrayList<>()),
                        (batchResult, triplet) -> {
                            if (triplet.getValue1()) {
                                batchResult.addSucceed(triplet.getValue2());
                            } else {
                                batchResult.addFailed(triplet.getValue0());
                            }
                        },
                        (batchBookingResult1, batchBookingResult2) -> {
                            List<Booking> combinedSucceedResults = new ArrayList<>();
                            combinedSucceedResults.addAll(batchBookingResult1.getSucceedBookingRequests());
                            combinedSucceedResults.addAll(batchBookingResult2.getSucceedBookingRequests());

                            List<BookingRequest> combinedFailedResults = new ArrayList<>();
                            combinedFailedResults.addAll(batchBookingResult1.getFailedBookingRequests());
                            combinedFailedResults.addAll(batchBookingResult2.getFailedBookingRequests());
                            return new BatchBookingResult(combinedFailedResults, combinedSucceedResults);
                        }
                )
        );
    }
}
