package org.kodigo.checkin.service.validation;

import org.kodigo.bookings.service.booking.IBookingService;

public final class PassengerMatchesBookingValidator extends BaseCheckInValidator {
    private final IBookingService bookings;

    public PassengerMatchesBookingValidator(IBookingService bookings){ this.bookings = bookings; }

    @Override
    public void validate(CheckInValidationContext ctx){
        var booking = bookings.getByCode(ctx.bookingCode());
        var passport = booking.passenger().passport();
        if (!passport.equals(ctx.passengerPassport())) {
            throw new IllegalArgumentException("Passenger does not match booking");
        }
        checkNext(ctx);
    }
}