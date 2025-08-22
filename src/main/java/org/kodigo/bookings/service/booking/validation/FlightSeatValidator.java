package org.kodigo.bookings.service.booking.validation;

import org.kodigo.flights.service.flight.IFlightService;

public final class FlightSeatValidator extends BaseValidator {
    private final IFlightService flights;
    public FlightSeatValidator(IFlightService flights){ this.flights = flights; }

    @Override public void validate(BookingValidationContext ctx){
        var flight = flights.getByCode(ctx.flightCode());

        if (flight.seatMap().freeSeats().isEmpty()) {
            throw new IllegalStateException("No seats available");
        }
        if (ctx.requestedSeat() != null && !flight.seatMap().isAvailable(ctx.requestedSeat())) {
            throw new IllegalStateException("Requested seat is occupied");
        }
        checkNext(ctx);
    }
}