package org.kodigo.bookings.service.seat;

import org.kodigo.flights.model.Flight;

public interface SeatAssignmentPolicy {
    String selectSeat(Flight flight, String requestedSeat);
}