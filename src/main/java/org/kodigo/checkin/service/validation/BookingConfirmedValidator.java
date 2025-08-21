package org.kodigo.checkin.service.validation;

import org.kodigo.bookings.model.Booking;
import org.kodigo.bookings.service.booking.IBookingService;

public final class BookingConfirmedValidator extends BaseCheckInValidator {
    private final IBookingService bookings;
    public BookingConfirmedValidator(IBookingService bookings){ this.bookings = bookings; }

    @Override
    public void validate(CheckInValidationContext ctx){
        var b = bookings.getByCode(ctx.bookingCode());
        if (b.isEmpty()) {
            throw new IllegalArgumentException("Booking not found: " + ctx.bookingCode());
        }
        var booking = b.get();
        if (booking.state() != Booking.BookingState.CONFIRMED) {
            throw new IllegalStateException("Booking is not CONFIRMED: " + ctx.bookingCode());
        }
        checkNext(ctx);
    }
}