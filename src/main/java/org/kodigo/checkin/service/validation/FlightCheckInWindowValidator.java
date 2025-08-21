package org.kodigo.checkin.service.validation;

import org.kodigo.bookings.service.booking.IBookingService;

import java.time.*;

public final class FlightCheckInWindowValidator extends BaseCheckInValidator {
    private final IBookingService bookings;
    private final Duration openBefore;   // e.g., PT48H
    private final Duration closeBefore;  // e.g., PT1H
    private final Clock clock;

    public FlightCheckInWindowValidator(IBookingService bookings, Duration openBefore, Duration closeBefore, Clock clock){
        this.bookings = bookings;
        this.openBefore = openBefore;
        this.closeBefore = closeBefore;
        this.clock = clock == null ? Clock.systemUTC() : clock;
    }

    @Override
    public void validate(CheckInValidationContext ctx){
        var b = bookings.getByCode(ctx.bookingCode());
        if (b.isEmpty()) {
            throw new IllegalArgumentException("Booking not found: " + ctx.bookingCode());
        }
        var booking = b.get();
        var flight = booking.flight();
        java.time.LocalDateTime dep = resolveDeparture(flight);
        var now = LocalDateTime.now(clock);

        if (now.isBefore(dep.minus(openBefore))) {
            throw new IllegalStateException("Check-in not open yet");
        }
        if (now.isAfter(dep.minus(closeBefore))) {
            throw new IllegalStateException("Check-in closed");
        }
        checkNext(ctx);
    }

    private LocalDateTime resolveDeparture(org.kodigo.flights.model.Flight f){
        try {
            // If you have f.departureAt(), use it:
            var m = org.kodigo.flights.model.Flight.class.getMethod("departureAt");
            Object v = m.invoke(f);
            if (v instanceof LocalDateTime ldt) return ldt;
        } catch (Exception ignore) { /* fall back */ }
        return f.date().atTime(12,0); // fallback if only date() exists
    }
}