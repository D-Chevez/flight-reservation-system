package org.kodigo.bookings.service.seat;

import org.kodigo.flights.model.Flight;

public final class FirstFreeSeatPolicy implements SeatAssignmentPolicy {

    @Override
    public String selectSeat(Flight flight, String requestedSeat) {
        if (requestedSeat != null && flight.seatMap().isAvailable(requestedSeat)) return requestedSeat;
        return flight.seatMap().freeSeats().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No free seats"));
    }
}