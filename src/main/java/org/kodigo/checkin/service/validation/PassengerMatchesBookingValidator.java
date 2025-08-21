package org.kodigo.checkin.service.validation;

import org.kodigo.bookings.service.booking.IBookingService;

public final class PassengerMatchesBookingValidator extends BaseCheckInValidator {
    private final IBookingService bookings;

    public PassengerMatchesBookingValidator(IBookingService bookings){ this.bookings = bookings; }

    @Override
    public void validate(CheckInValidationContext ctx){
        var b = bookings.getByCode(ctx.bookingCode());
        if (b.isEmpty()) {
            throw new IllegalArgumentException("Booking not found: " + ctx.bookingCode());
        }
        var booking = b.get();
        var passport = booking.passenger().passport();
        if (!passport.equals(ctx.passengerPassport())) {
            throw new IllegalArgumentException("Passenger does not match booking");
        }
        checkNext(ctx);
    }
}