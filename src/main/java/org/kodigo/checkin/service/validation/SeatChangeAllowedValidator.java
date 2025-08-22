package org.kodigo.checkin.service.validation;

import org.kodigo.bookings.service.booking.IBookingService;

public final class SeatChangeAllowedValidator extends BaseCheckInValidator {
    private final IBookingService bookings;
    private final boolean allowSeatChange;

    public SeatChangeAllowedValidator(IBookingService bookings, boolean allowSeatChange){
        this.bookings = bookings;
        this.allowSeatChange = allowSeatChange;
    }

    @Override
    public void validate(CheckInValidationContext ctx){
        if (!allowSeatChange && ctx.requestedSeat() != null) {
            var booking = bookings.getByCode(ctx.bookingCode());
            var current = booking.seatNumber();
            if (!current.equals(ctx.requestedSeat())) {
                throw new IllegalStateException("Seat changes are not allowed at check-in");
            }
        }
        checkNext(ctx);
    }
}
